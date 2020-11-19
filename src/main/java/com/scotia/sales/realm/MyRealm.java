package com.scotia.sales.realm;

import com.scotia.sales.entity.Menu;
import com.scotia.sales.entity.Role;
import com.scotia.sales.entity.User;
import com.scotia.sales.repository.MenuRepository;
import com.scotia.sales.repository.RoleRepository;
import com.scotia.sales.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义realm，权限认证
 */
public class MyRealm extends AuthorizingRealm {

    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private MenuRepository menuRepository;


    /**
     * 授权
     *
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取用户名
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        //查询用户信息
        User user = userRepository.findByUserName(userName);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //查询当前用户对应的角色列表
        List<Role> roleList = roleRepository.findByUserId(user.getId());
        Set<String> roles = new HashSet<>();
        roleList.forEach(role -> {
            //排除重复的角色名称
            roles.add(role.getName());
            //获取当前用户对应的角色对应的菜单列表
            List<Menu> menuList = menuRepository.findByRoleId(role.getId());
            menuList.forEach(menu -> {
                //将当前用户，当前角色对应的菜单名称，添加到认证信息类SimpleAuthorizationInfo
                info.addStringPermission(menu.getName());
            });
        });
        info.setRoles(roles);
        return info;
    }

    /**
     * 权限认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        User user = userRepository.findByUserName(userName);
        return user == null ? null : new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), "xxx");
    }
}
