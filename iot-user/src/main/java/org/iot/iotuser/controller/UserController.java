package org.iot.iotuser.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import org.iot.iotcommon.config.BaseResult;
import org.iot.iotcommon.model.PageCondition;
import org.iot.iotcommon.model.User;
import org.iot.iotuser.service.UserService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "用户", description = "用户管理 API", position = 100, protocols = "${custom.protocol}")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService service;

    @ApiOperation(value = "获取用户列表", notes = "查询用户列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public BaseResult<List<User>> selectList() {
        return BaseResult.successWithData(service.selectList());
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
    @RequestMapping(value = "selectById", method = RequestMethod.GET)
    public BaseResult<User> selectById(@RequestParam("id") String id) {
        User user = service.selectById(id);
        if (user == null) return BaseResult.failWithStatusAndMsg(false, "获取不到数据");
        return BaseResult.successWithData(service.selectById(id));
    }

    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public BaseResult<User> addUser(@ApiIgnore @RequestBody User user) {
        User u = service.selectByEntity(user);
        if (u != null) return BaseResult.failWithStatusAndMsg(false, "添加失败，用户信息已存在");
        return BaseResult.successWithData(service.insert(user));
    }

    @ApiOperation(value = "查询用户", notes = "根据User对象查询用户")
    @RequestMapping(value = "selectByEntity", method = RequestMethod.POST)
    public BaseResult<User> selectByEntity(@RequestBody User user) {
        User us = service.selectByEntity(user);
        System.out.println("----------------------------------------------------");
        System.out.println(us);
        if (us == null) return BaseResult.failWithStatusAndMsg(false, "查询失败，用户信息不存在");
        return BaseResult.successWithData(us);
    }

    @ApiOperation(value = "更新用户", notes = "根据User对象更新用户")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public BaseResult<User> updateUser(@RequestBody User user) {
        User u = service.updateById(user);
        if (u == null) return BaseResult.failWithStatusAndMsg(false, "更新失败");
        return BaseResult.successWithData(service.updateById(user));
    }

    @ApiOperation(value = "删除用户", notes = "根据url的id来删除用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public BaseResult<User> deleteById(@RequestParam("id") String id) {
        boolean bool = service.deleteById(id);
        return BaseResult.successWithStatusAndMsg(bool, "删除用户" + (bool ? "成功" : "失败"));
    }

    @ApiOperation(value = "批量删除用户", notes = "根据url的id数组来删除用户信息")
    @ApiImplicitParam(name = "list", value = "用户ID数组", required = true, allowMultiple = true, dataType = "String")
    @RequestMapping(value = "deleteBatchIds", method = RequestMethod.DELETE)
    public BaseResult<Boolean> deleteBatchIds(@RequestBody List<String> list) {
        boolean bool = service.deleteBatchIds(list);
        return BaseResult.successWithStatusAndMsg(bool, "删除用户" + (bool ? "成功" : "失败"));
    }

    @ApiOperation(value = "分页获取用户信息", notes = "根据current和size来获取用户列表数据")
    @RequestMapping(value = "selectPage", method = RequestMethod.POST)
    public BaseResult<IPage<User>> selectPage(@RequestBody PageCondition pageCondition) {
        //public BaseResult<IPage<User>> selectPage(@RequestBody PageCondition pageCondition, HttpServletRequest httpServletRequest) {
        //    String header = httpServletRequest.getHeader("X-Forwarded-For");
        //    System.out.println(header);
        String keyword = pageCondition.getKeyword();
        List<String> columns = pageCondition.getColumns();
        if (keyword != null && keyword.trim().length() > 0) {
            if (columns == null || columns.size() < 1) {
                return BaseResult.failWithStatusAndMsg(false, "需要指定模糊搜索的列");
            }
        }
        return BaseResult.successWithData(service.selectPage(pageCondition));
    }

    @ApiOperation(value = "模糊分页获取用户信息", notes = "根据关键字、current和size来获取用户列表数据")
    @RequestMapping(value = "selectPageByKeyword", method = RequestMethod.POST)
    public BaseResult<IPage<User>> selectPageByKeyword(@RequestBody PageCondition pageCondition) {
        return BaseResult.successWithData(service.selectPage(pageCondition));
    }
}
