package cm.zy.service;

import cm.zy.pojo.Offer;

import java.util.List;

public interface OfferService {
    List<Offer> list();

    void update(Offer offer);

    void delete(Integer id);

    void add(Offer ofert);

    Offer getOfferById(Integer id);
}