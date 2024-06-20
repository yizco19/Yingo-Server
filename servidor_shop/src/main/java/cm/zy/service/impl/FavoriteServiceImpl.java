package cm.zy.service.impl;

import cm.zy.mapper.FavoriteMapper;
import cm.zy.pojo.Favorite;
import cm.zy.service.FavoriteService;
import cm.zy.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteMapper favoriteMapper;
    @Override
    public void add(Favorite favorite) {
        if(favorite.getUserId() == null){
            Map<String, Object> map = ThreadLocalUtil.get();
            int id = (int) map.get("id");
            favorite.setUserId(id);
        }
        favoriteMapper.add(favorite);

    }

    @Override
    public void delete(Integer productId) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        favoriteMapper.delete(productId, userId);
    }

    @Override
    public List<Favorite> list() {
        return favoriteMapper.list();
    }

    @Override
    public void deleteAll() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        favoriteMapper.deleteAll(userId);
    }
}
