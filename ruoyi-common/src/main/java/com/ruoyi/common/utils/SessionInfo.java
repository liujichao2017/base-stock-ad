package com.ruoyi.common.utils;

import com.ruoyi.common.core.domain.entity.SysRole;

import java.util.List;
import java.util.stream.Collectors;

public class SessionInfo {
    public static Long getUserId() {
        return SecurityUtils.getUserId();
    }

    public static boolean isAgent() {
        return !"all".equals(SecurityUtils.getLoginUser().getUser().getDataType());
    }

    public static List<SysRole> roleList(List<SysRole> sysRoles) {
        List<SysRole> roles = SecurityUtils.getLoginUser().getUser().getRoles();
        List<Long> roleIds = roles.stream().map(SysRole::getRoleId).collect(Collectors.toList());
        int roleLevel = Integer.MAX_VALUE;
        for (SysRole sysRole : sysRoles) {
            if (roleIds.contains(sysRole.getRoleId())) {
                roleLevel = roleLevel > sysRole.getRoleSort() ? sysRole.getRoleSort() : roleLevel;
            }
        }
        int tmp = roleLevel;
        return sysRoles.stream().filter(r -> r.getRoleSort() >= tmp).collect(Collectors.toList());
    }
}
