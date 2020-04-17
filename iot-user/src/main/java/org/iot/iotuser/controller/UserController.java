package org.iot.iotuser.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import org.iot.iotcommon.config.BaseResult;
import org.iot.iotuser.model.User;
import org.iot.iotuser.service.UserService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "消息", description = "消息操作 API", position = 100, protocols = "${custom.protocol}")
@RestController
public class UserController {
    @Resource
    private UserService service;

    @ApiOperation(value = "获取用户列表", notes = "查询用户列表")
    @ApiResponses({
            @ApiResponse(code = 100, message = "异常数据")
    })
    @RequestMapping("/user/list")
    public BaseResult<List<User>> selectList() {
        return BaseResult.successWithData(service.selectList());
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/selectById", method = RequestMethod.GET)
    public BaseResult<User> selectById(@RequestParam("id") Long id) {
        User user = service.selectById(id);
        if (user == null) return BaseResult.failWithCodeAndMsg(0, "为获取到数据");
        return BaseResult.successWithData(service.selectById(id));
    }

    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "age", value = "年龄", required = true, dataType = "String"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String")
    })
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public BaseResult<User> addUser(@ApiIgnore @RequestBody User user) {
        User u = service.selectById(user.getId());
        if (u != null) return BaseResult.failWithCodeAndMsg(0, "添加失败，用户信息已存在");
        return BaseResult.successWithData(service.insert(user));
    }

    @ApiOperation(value = "更新用户", notes = "根据User对象更新用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "age", value = "年龄", required = true, dataType = "String"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String")
    })
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public BaseResult<User> updateUser(@RequestBody User user) {
        return BaseResult.successWithData(service.updateById(user));
    }

    @ApiOperation(value = "删除用户", notes = "根据url的id来删除用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/user/delete", method = RequestMethod.DELETE)
    public BaseResult<User> deleteUser(@RequestParam("id") Long id) {
        return BaseResult.successWithCodeAndMsg(0, service.deleteById(id) ? "成功" : "失败");
    }

    @ApiOperation(value = "分页获取用户信息", notes = "根据current和size来获取用户列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页的条数", required = true, dataType = "Integer")
    })
    @RequestMapping("/selectPage")
    public BaseResult<IPage<User>> selectPage(@RequestParam int current, @RequestParam int size) {
        return BaseResult.successWithData(service.selectPage(current, size));
    }
}
