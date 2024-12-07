package com.laptop.service;

import com.laptop.dao.ProductDAO;
import com.laptop.dao.PromotionDAO;

public class PromotionService extends PromotionDAO {
    public PromotionService() {}

    private PromotionDAO getPromotionDAO() {
        return new PromotionDAO();
    }


}
