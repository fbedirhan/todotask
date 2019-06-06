package com.task.todolist;

import com.task.todolist.models.JsonResponseModel;
import com.task.todolist.models.TodoItemModel;
import com.task.todolist.models.TodoListModel;
import com.task.todolist.models.UserModel;
import com.task.todolist.repositories.UserRepository;
import com.task.todolist.services.TodoListServiceInterface;
import com.task.todolist.services.UserServiceInterface;
import com.task.todolist.utils.Constant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

public class TodoListUnitTest extends AbstractTest {

    private UserModel mUser;

    @Autowired
    private UserServiceInterface mUserService;

    @Autowired
    private TodoListServiceInterface mTodoListService;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        mUser = new UserModel();
        mUser.setName("Bedirhan");
        mUser.setSurname("Yıldız");
        mUser.setEmail("fby@gmail.com");
        mUser.setPassword("123456");
        mUserService.register(mUser);

        TodoListModel todoList1 = new TodoListModel();
        todoList1.setName("test 1");
        todoList1.setUser(mUser);
        mTodoListService.createTodoList(todoList1);

        TodoListModel todoList2 = new TodoListModel();
        todoList2.setName("test 2");
        todoList2.setUser(mUser);
        mTodoListService.createTodoList(todoList2);

    }


    @Test
    public void createItemToList() throws Exception {
        String uri = Constant.BASE_URL + "todoItem/create";
        TodoItemModel itemModel = new TodoItemModel();
        itemModel.setName("test 1");
        itemModel.setDescription("Test 1 Açıklama");
        itemModel.setDeadline(new Date());
        itemModel.setTodoList(mTodoListService.findAllTodoListByUserId(mUser.getId()).get(0));

        String inputJson = super.mapToJson(itemModel);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        JsonResponseModel response = super.mapFromJson(content, JsonResponseModel.class);
        assertEquals(response.isSuccess(), true);
    }

    @Test
    public void deleteTodoList() throws Exception {
        String uri = Constant.BASE_URL + "todoList/delete/" + mTodoListService.findAllTodoListByUserId(mUser.getId()).get(0).getId() + "/" + mUser.getId();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        JsonResponseModel response = super.mapFromJson(content, JsonResponseModel.class);
        assertEquals(response.isSuccess(), true);
    }

    @Test
    public void getDependencies() throws Exception {
        String uri = Constant.BASE_URL + "todoItem/dependency/2";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        JsonResponseModel response = super.mapFromJson(content, JsonResponseModel.class);
        assertEquals(response.isSuccess(), true);
    }
}
