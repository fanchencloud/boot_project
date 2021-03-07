package cn.fanchencloud.boot_project.service.impl;


import cn.fanchencloud.boot_project.entity.User;
import cn.fanchencloud.boot_project.enums.ResultEnum;
import cn.fanchencloud.boot_project.exception.CustomException;
import cn.fanchencloud.boot_project.form.user.AddUserForm;
import cn.fanchencloud.boot_project.form.user.ListUserForm;
import cn.fanchencloud.boot_project.mapper.UserMapper;
import cn.fanchencloud.boot_project.service.IUserService;
import cn.fanchencloud.boot_project.utils.MethodUtil;
import cn.fanchencloud.boot_project.vo.PageVo;
import cn.fanchencloud.boot_project.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huangxunhui
 * @date Created in 2020/3/6 4:50 下午
 * Utils: Intellij Idea
 * Description: 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private UserMapper userMapper;

    /**
     * 添加用户
     * @param userForm 表单数据
     * @return true 或者 false
     */
    @Override
    public boolean addUser(AddUserForm userForm) {
        return save(userForm.buildEntity());
    }

    /**
     * 获取用户列表
     * @param listUserForm 表单数据
     * @return 用户列表
     */
    @Override
    public PageVo<UserVo> listUser(ListUserForm listUserForm) {
        PageVo<UserVo> pageVo = new PageVo<UserVo>().setCurrentAndSize(listUserForm);
        Integer countUser = countUser(listUserForm.getStatus());
        pageVo.setTotal(countUser);
        List<UserVo> userVoList = userMapper.listUser(listUserForm.calcCurrent());
        pageVo.setRecords(userVoList);
        return pageVo;
    }

    /**
     * 删除用户
     * @param id id
     */
    @Override
    public void deleteUser(String id) {
        // 如果删除失败抛出异常。 -- 演示而已不推荐这样干
        if(!removeById(id)){
            throw new CustomException(ResultEnum.DELETE_ERROR, MethodUtil.getLineInfo());
        }
    }

    /**
     * 获取用户数量
     * @param status 状态
     * @return 用户数量
     */
    private Integer countUser(String status){
        return count(new QueryWrapper<User>().eq("status",status));
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
