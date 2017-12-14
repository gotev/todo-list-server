package net.gotev.todolist

import net.gotev.todolist.server.ToDoItem
import net.gotev.todolist.server.ToDoList
import org.apache.thrift.async.AsyncMethodCallback

class ToDoListRPCService : ToDoList.AsyncIface {

    override fun getToDo(accessToken: String?, resultHandler: AsyncMethodCallback<MutableList<ToDoItem>>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addItem(accessToken: String?, content: String?, resultHandler: AsyncMethodCallback<ToDoItem>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setItemDone(username: String?, itemID: String?, resultHandler: AsyncMethodCallback<Boolean>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteItem(username: String?, itemID: String?, resultHandler: AsyncMethodCallback<Boolean>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}