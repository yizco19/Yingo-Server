package cm.zy.service.impl;

import cm.zy.mapper.AdminMapper;
import cm.zy.pojo.Admin;
import cm.zy.pojo.StatisticsDTO;
import cm.zy.service.AdminService;
import cm.zy.utils.PasswordUtil;
import cm.zy.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findByUsername(String username) {
        Admin c = adminMapper.findByUsername(username);
        return c;
    }

    @Override
    public void register(String username, String email, String password) {
        // codificar password
        String md5Password = PasswordUtil.encodePassword(password);
        // registrar cliente
        adminMapper.add(username,email,md5Password);

    }

    @Override
    public void update(Admin admin) {

        adminMapper.update(admin);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        adminMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPassword) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        adminMapper.updatePwd(PasswordUtil.encodePassword(newPassword),id);

    }

    @Override
    public Admin findByEmail(String email) {
        Admin admin = adminMapper.findByEmail(email);
        return admin;
    }

    @Override
    public List<Admin> list() {
        List<Admin> admins = adminMapper.list();
        return admins;
    }

    @Override
    public Admin findById(Integer userId) {
        Admin admin = adminMapper.findById(userId);
        return admin;
    }

    @Override
    public Admin findByEmailOrUsername(String emailOrUsername) {
        Admin admin = adminMapper.findByEmailOrUsername(emailOrUsername);
        return admin;
    }

    @Override
    public Admin findByUsernameAndId(String username, Integer id) {
        Admin admin = adminMapper.findByUsernameAndId(username,id);
        return admin;
    }

    @Override
    public StatisticsDTO getStatistics() {
        long userCount = adminMapper.countUsers();
        long productCount = adminMapper.countProducts();
        long orderCount = adminMapper.countOrders();
        double totalRevenue = adminMapper.totalRevenue();

        List<Double> monthlyRevenue = adminMapper.monthlyRevenue();
        StatisticsDTO statisticsDTO = new StatisticsDTO(userCount,productCount,orderCount,totalRevenue,monthlyRevenue);

        return statisticsDTO;
    }



}
