package cm.zy.service.impl;

import cm.zy.mapper.OfferMapper;
import cm.zy.pojo.Offer;
import cm.zy.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferMapper offerMapper;

    @Override
    public List<Offer> list() {
        return offerMapper.list();
    }

    @Override
    public void update(Offer offer) {
        offerMapper.update(offer);

    }

    @Override
    public void delete(Integer id) {
        offerMapper.delete(id);


    }

    @Override
    public void add(Offer offer) {
        offerMapper.add(offer);


    }

    @Override
    public Offer getOfferById(Integer id) {
        return offerMapper.getOfferById(id);
    }
}