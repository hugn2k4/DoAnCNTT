package com.laptop.dao;

import com.laptop.models.Promotion;
import com.laptop.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class PromotionDAO extends AbstractDAO<Promotion> {
    public PromotionDAO() {
        super(Promotion.class);
    }

    @Override
    public long insert(Promotion promotion) {
        return super.insert(promotion);
    }

    @Override
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    public void update(Promotion promotion) {
        super.update(promotion);
    }

    @Override
    public Optional<Promotion> getById(long id) {
        return super.getById(id);
    }

    @Override
    public List<Promotion> getAll() {
        return super.getAll();
    }
    public Optional<Promotion> getByPromotionId(long productId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select c from Promotion c where c.id = :productId";
            return session.createQuery(hql, Promotion.class)
                    .setParameter("productId", productId)
                    .uniqueResultOptional();
        }
    }

    public int count() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery("select count(id) from Promotion", Long.class)
                    .uniqueResult();
            return count != null ? count.intValue() : 0;
        }
    }
}
