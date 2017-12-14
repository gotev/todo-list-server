package net.gotev.todolist

import mu.KLogger
import mu.KotlinLogging
import org.apache.thrift.protocol.TCompactProtocol
import org.apache.thrift.server.THsHaServer
import org.apache.thrift.transport.TFramedTransport
import org.apache.thrift.transport.TNonblockingServerSocket
import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit

// Server configuration. Should be moved to a file based on the environment,
// but here only for demo simplicity
val serverVersion = "1.0"
val thriftServerPort = 26000 // Use ports above 25000 to avoid rejections by Apple AppReview Team 😄
val thriftServerListenOn = "0.0.0.0" // Set "::" to start the server with IPv6 stack (not needed if you're on AWS)

fun main(args: Array<String>) {

    val log = KotlinLogging.logger("ToDoServerMain")
    val maxHeapMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024

    log.info { "Starting ToDo List server $serverVersion with max heap: ${maxHeapMemory}MB" }

    startThriftServer(log)
}

fun startThriftServer(log: KLogger) {
    val transport = TNonblockingServerSocket(InetSocketAddress(thriftServerListenOn, thriftServerPort))
    val framedTransportMaxLength = 16384000L //16MB

    val hsHaConfig = THsHaServer.Args(transport)
            .stopTimeoutVal(30)
            .stopTimeoutUnit(TimeUnit.SECONDS)
            .maxWorkerThreads(10)
            .transportFactory(TFramedTransport.Factory())
            .protocolFactory(TCompactProtocol.Factory(framedTransportMaxLength, framedTransportMaxLength))
            .processor(null)

    hsHaConfig.maxReadBufferBytes = 4096

    val server = THsHaServer(hsHaConfig)

    shutdownHook {
        log.info { "SIGTERM Detected, shutting down Thrift RPC server..." }
        server.stop()
    }

    log.info { "Starting Thrift RPC server on $thriftServerListenOn:$thriftServerPort" }
    server.serve()
}

fun shutdownHook(runnable: () -> Unit) {
    Runtime.getRuntime().addShutdownHook(Thread(runnable))
}