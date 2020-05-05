package org.iot.iotuser.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.iot.iotcommon.config.BaseResult;
import org.iot.iotcommon.model.PageCondition;
import org.iot.iotcommon.model.Org;
import org.iot.iotuser.service.OrgService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "组织机构", description = "组织机构管理 API", position = 100, protocols = "${custom.protocol}")
@RestController
@RequestMapping("/org")
public class OrgController {
    @Resource
    private OrgService orgService;

    @ApiOperation(value = "获取组织列表", notes = "查询组织列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public BaseResult<List<Org>> selectList() {
        return BaseResult.successWithData(orgService.selectList());
    }

    @ApiOperation(value = "获取组织详细信息", notes = "根据url的id来获取组织详细信息")
    @ApiImplicitParam(name = "id", value = "组织ID", required = true, dataType = "String")
    @RequestMapping(value = "selectById", method = RequestMethod.GET)
    public BaseResult<Org> selectById(@RequestParam("id") String id) {
        Org org = orgService.selectById(id);
        if (org == null) return BaseResult.failWithStatusAndMsg(false, "获取不到数据");
        return BaseResult.successWithData(orgService.selectById(id));
    }

    @ApiOperation(value = "创建组织", notes = "根据Org对象创建组织")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public BaseResult<Org> addOrg(@ApiIgnore @RequestBody Org org) {
        if (org == null) return BaseResult.failWithStatusAndMsg(false, "参数不正确");
        Org o = orgService.insert(org);
        if (o == null) return BaseResult.failWithStatusAndMsg(false, "添加失败");
        return BaseResult.successWithData(o);
    }

    @ApiOperation(value = "更新组织", notes = "根据Org对象更新组织")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public BaseResult<Org> updateOrg(@RequestBody Org org) {
        Org o = orgService.updateById(org);
        if (o == null) return BaseResult.failWithStatusAndMsg(false, "更新失败");
        return BaseResult.successWithData(orgService.updateById(org));
    }

    @ApiOperation(value = "删除组织", notes = "根据url的id来删除组织信息")
    @ApiImplicitParam(name = "id", value = "组织ID", required = true, dataType = "String")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public BaseResult<Org> deleteById(@RequestParam("id") String id) {
        boolean bool = orgService.deleteById(id);
        return BaseResult.successWithStatusAndMsg(bool, "删除组织" + (bool ? "成功" : "失败"));
    }

    @ApiOperation(value = "批量删除组织", notes = "根据url的id数组来删除组织信息")
    @ApiImplicitParam(name = "list", value = "组织ID数组", required = true, allowMultiple = true, dataType = "String")
    @RequestMapping(value = "deleteBatchIds", method = RequestMethod.DELETE)
    public BaseResult<Boolean> deleteBatchIds(@RequestBody List<String> list) {
        boolean bool = orgService.deleteBatchIds(list);
        return BaseResult.successWithStatusAndMsg(bool, "删除组织" + (bool ? "成功" : "失败"));
    }

    @ApiOperation(value = "模糊分页获取组织信息", notes = "根据关键字、current和size来获取组织列表数据")
    @RequestMapping(value = "selectPageByKeyword", method = RequestMethod.POST)
    public BaseResult<IPage<Org>> selectPageByKeyword(@RequestBody PageCondition pageCondition) {
        return BaseResult.successWithData(orgService.selectPage(pageCondition));
    }
}
