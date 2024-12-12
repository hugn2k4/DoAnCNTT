package com.laptop.service;

import com.laptop.dao.ProductDAO;
import com.laptop.dao.PromotionDAO;
import com.laptop.models.Promotion;
import java.util.List;

public class PromotionService extends PromotionDAO {
    public PromotionService() {}

    private PromotionDAO getPromotionDAO() {
        return new PromotionDAO();
    }

    private final PromotionDAO promotionDAO = new PromotionDAO();

    public List<Promotion> getPromotions(int limit, int offset) {
        return promotionDAO.getPromotions(limit, offset);  // Trả về danh sách quảng cáo theo phân trang
    }

    public int countPromotions() {
        return promotionDAO.count();  // Trả về tổng số quảng cáo
    }
}
