package cm.zy.service;

import cm.zy.pojo.Favorite;

import java.util.List;

public interface FavoriteService {

    void add(Favorite favorite);
    void delete(Integer productId);
    List<Favorite> list();

    void deleteAll();
}
