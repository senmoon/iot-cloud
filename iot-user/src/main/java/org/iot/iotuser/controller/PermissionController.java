package org.iot.iotuser.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.iot.iotcommon.config.BaseResult;
import org.iot.iotcommon.model.PageCondition;
import org.iot.iotcommon.model.Permission;
import org.iot.iotuser.service.PermissionService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "权限", description = "权限管理 API", position = 100, protocols = "${custom.protocol}")
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Resource
    private PermissionService service;

    @ApiOperation(value = "获取权限列表", notes = "查询权限列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public BaseResult<List<Permission>> selectList() {
        return BaseResult.successWithData(service.selectList());
    }

    @ApiOperation(value = "获取权限详细信息", notes = "根据url的id来获取权限详细信息")
    @ApiImplicitParam(name = "id", value = "权限ID", required = true, dataType = "String")
    @RequestMapping(value = "selectById", method = RequestMethod.GET)
    public BaseResult<Permission> selectById(@RequestParam("id") String id) {
        Permission permission = service.selectById(id);
        if (permission == null) return BaseResult.failWithStatusAndMsg(false, "获取不到数据");
        return BaseResult.successWithData(permission);
    }

    @ApiOperation(value = "创建权限", notes = "根据Permission对象创建权限")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public BaseResult<Permission> addPermission(@ApiIgnore @RequestBody Permission permission) {
        if (permission == null) return BaseResult.failWithStatusAndMsg(false, "参数不正确");
        Permission per = service.insert(permission);
        if (per == null) return BaseResult.failWithStatusAndMsg(false, "添加失败");
        return BaseResult.successWithData(service.selectById(permission.getId()));
    }

    @ApiOperation(value = "更新权限", notes = "根据Permission对象更新权限")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public BaseResult<Permission> updatePermission(@RequestBody Permission permission) {
        Permission per = service.updateById(permission);
        if (per == null) return BaseResult.failWithStatusAndMsg(false, "更新失败");
        return BaseResult.successWithData(service.selectById(permission.getId()));
    }

    @ApiOperation(value = "删除权限", notes = "根据url的id来删除权限信息")
    @ApiImplicitParam(name = "id", value = "权限ID", required = true, dataType = "String")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public BaseResult<Permission> deleteById(@RequestParam("id") String id) {
        boolean bool = service.deleteById(id);
        return BaseResult.successWithStatusAndMsg(bool, "删除权限" + (bool ? "成功" : "失败"));
    }

    @ApiOperation(value = "批量删除权限", notes = "根据url的id数组来删除权限信息")
    @ApiImplicitParam(name = "list", value = "权限ID数组", required = true, allowMultiple = true, dataType = "String")
    @RequestMapping(value = "deleteBatchIds", method = RequestMethod.DELETE)
    public BaseResult<Boolean> deleteBatchIds(@RequestBody List<String> list) {
        boolean bool = service.deleteBatchIds(list);
        return BaseResult.successWithStatusAndMsg(bool, "删除权限" + (bool ? "成功" : "失败"));
    }

    @ApiOperation(value = "模糊分页获取权限信息", notes = "根据关键字、current和size来获取权限列表数据")
    @RequestMapping(value = "selectPageByKeyword", method = RequestMethod.POST)
    public BaseResult<IPage<Permission>> selectPageByKeyword(@RequestBody PageCondition pageCondition) {
        return BaseResult.successWithData(service.selectPage(pageCondition));
    }
}
