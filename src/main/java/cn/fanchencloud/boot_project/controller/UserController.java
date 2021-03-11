package cn.fanchencloud.boot_project.controller;

import cn.fanchencloud.boot_project.entity.User;
import cn.fanchencloud.boot_project.enums.ResultEnum;
import cn.fanchencloud.boot_project.form.user.AddUserForm;
import cn.fanchencloud.boot_project.form.user.ListUserForm;
import cn.fanchencloud.boot_project.service.IUserService;
import cn.fanchencloud.boot_project.service.redis.RedisService;
import cn.fanchencloud.boot_project.utils.ResultVoUtil;
import cn.fanchencloud.boot_project.vo.ResultVo;
import cn.fanchencloud.boot_project.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by handsome programmer.
 *
 * @author chen
 * @User: chen
 * @Date: 2021/3/8
 * @Time: 0:23
 * @Description:
 */
@RestController
@Api(tags = "关于用户的操作")
@RequestMapping("/user")
public class UserController {

    private IUserService userService;

    private RedisService redisService;

    @ApiOperation("问候请求")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }

    /**
     * 添加用户
     *
     * @param userForm 表单数据
     * @return 成功或者失败
     */
    @ApiOperation("添加用户")
    @PostMapping("/addUser")
    public ResultVo addUser(@Validated @RequestBody AddUserForm userForm) {
        if (userService.addUser(userForm)) {
            return ResultVoUtil.success();
        } else {
            return ResultVoUtil.error(ResultEnum.ADD_ERROR);
        }
    }

    /**
     * 获取用户列表
     *
     * @param listUserForm 表单数据
     * @return 用户列表
     */
    @ApiOperation("获取用户列表")
    @GetMapping("/listUser")
    @ApiResponses(
            @ApiResponse(code = 200, message = "操作成功", response = UserVo.class)
    )
    public ResultVo listUser(@Validated ListUserForm listUserForm) {
        return ResultVoUtil.success(userService.listUser(listUserForm));
    }

    /**
     * 删除用户
     *
     * @param id 用户编号
     * @return 成功或者失败
     */
    @ApiOperation("删除用户")
    @DeleteMapping("/deleteUser/{id}")
    public ResultVo deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return ResultVoUtil.success();
    }

    /*------------------------- Test String Key -------------------------------*/
    @GetMapping("/testSetStringKey/{id}")
    public String testSetStringKey(@PathVariable Integer id) {
        User user = new User();
        user.setId(id);
        user.setNickname("fanchen" + id);
        Calendar myCalendar = new GregorianCalendar(2014, 2, 11);
        Date myDate3 = myCalendar.getTime();
        user.setBirthday(myDate3);
        user.setUsername(user.getNickname());
        user.setPassword("123456");
        // 写入缓存
        redisService.setValue("testStringKey-" + id, user);
        return "缓存写入成功";
    }

    @GetMapping("/testGetStringKey/{id}")
    public ResultVo testGetStringKey(@PathVariable Integer id) {
        // 读取缓存
        User stu = (User) redisService.getValue("testStringKey-" + id);
        return ResultVoUtil.success(stu);
    }


    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}
