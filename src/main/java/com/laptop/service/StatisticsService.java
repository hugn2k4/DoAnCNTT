package com.laptop.service;

import com.laptop.models.Product;
import com.laptop.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
public class StatisticsService {

    public List<Object[]> getDailyStatistics() {
        String hqlByDay = "SELECT DAY(u.registrationDate) AS day, COUNT(u) AS userCount " +
                "FROM User u " +
                "WHERE MONTH(u.registrationDate) = MONTH(CURRENT_DATE) " +
                "AND YEAR(u.registrationDate) = YEAR(CURRENT_DATE) " +
                "GROUP BY DAY(u.registrationDate)";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(hqlByDay, Object[].class);
            return query.list();
        }
    }

    public List<Object[]> getMonthlyStatisticsCurrentYear() {
        String hqlByMonthCurrentYear = "SELECT MONTH(u.registrationDate) AS month, COUNT(u) AS userCount " +
                "FROM User u " +
                "WHERE YEAR(u.registrationDate) = YEAR(CURRENT_DATE) " +
                "GROUP BY MONTH(u.registrationDate)";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(hqlByMonthCurrentYear, Object[].class);
            return query.list();
        }
    }

    public List<Object[]> getMonthlyStatisticsLastYear() {
        String hqlByMonthLastYear = "SELECT MONTH(u.registrationDate) AS month, COUNT(u) AS userCount " +
                "FROM User u " +
                "WHERE YEAR(u.registrationDate) = YEAR(CURRENT_DATE) - 1 " +
                "GROUP BY MONTH(u.registrationDate)";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(hqlByMonthLastYear, Object[].class);
            return query.list();
        }
    }

    public List<Product> getTotalBuyStatistics() {
        List<Product> products = new ArrayList<>();
        String hql = "SELECT p.name, p.totalBuy FROM Product p"; // Chỉ lấy tên và totalBuy của sản phẩm
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            List<Object[]> result = query.list();
            for (Object[] row : result) {
                Product product = new Product();
                product.setName((String) row[0]); // Lưu tên sản phẩm

                // Kiểm tra và chuyển đổi kiểu nếu cần thiết (Long -> Integer)
                Object totalBuyObj = row[1];
                if (totalBuyObj instanceof Long) {
                    product.setTotalBuy(((Long) totalBuyObj).intValue()); // Chuyển Long thành Integer nếu là Long
                } else if (totalBuyObj instanceof Integer) {
                    product.setTotalBuy((Integer) totalBuyObj); // Nếu là Integer thì trực tiếp gán
                }

                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
    public List<Object[]> getDailyRevenueStatistics() {
        String hqlByDayRevenue = "SELECT DAY(o.createdAt) AS day, SUM(o.totalPrice) AS dailyRevenue " + // Thay 'orderDate' thành 'createdAt' và 'totalAmount' thành 'totalPrice'
                "FROM Order o " +
                "WHERE MONTH(o.createdAt) = MONTH(CURRENT_DATE) " + // Thay 'orderDate' thành 'createdAt'
                "AND YEAR(o.createdAt) = YEAR(CURRENT_DATE) " + // Thay 'orderDate' thành 'createdAt'
                "GROUP BY DAY(o.createdAt)"; // Thay 'orderDate' thành 'createdAt'
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(hqlByDayRevenue, Object[].class);
            return query.list();
        }
    }

    public List<Object[]> getMonthlyRevenueStatisticsCurrentYear() {
        String hqlByMonthRevenue = "SELECT MONTH(o.createdAt) AS month, SUM(o.totalPrice) AS monthlyRevenue " + // Thay 'orderDate' thành 'createdAt' và 'totalAmount' thành 'totalPrice'
                "FROM Order o " +
                "WHERE YEAR(o.createdAt) = YEAR(CURRENT_DATE) " + // Thay 'orderDate' thành 'createdAt'
                "GROUP BY MONTH(o.createdAt)"; // Thay 'orderDate' thành 'createdAt'
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(hqlByMonthRevenue, Object[].class);
            return query.list();
        }
    }

    public List<Product> getTotalProductSoldStatisticsCurrentYear() {
        List<Product> products = new ArrayList<>();
        String hql = "SELECT p.name, SUM(oi.quantity) AS totalSold " +
                "FROM OrderItem oi JOIN oi.product p " +
                "WHERE YEAR(oi.order.createdAt) = YEAR(CURRENT_DATE) " + // Thay 'orderDate' thành 'createdAt'
                "GROUP BY p.name";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            List<Object[]> result = query.list();
            for (Object[] row : result) {
                Product product = new Product();
                product.setName((String) row[0]);
                product.setTotalBuy(((Long) row[1]).intValue());
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getTopSellingProducts() {
        List<Product> products = new ArrayList<>();
        String hql = "SELECT p.name, SUM(oi.quantity) AS totalSold " +
                "FROM OrderItem oi JOIN oi.product p " +
                "GROUP BY p.name " +
                "ORDER BY totalSold DESC";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            List<Object[]> result = query.list();
            for (Object[] row : result) {
                Product product = new Product();
                product.setName((String) row[0]);
                product.setTotalBuy(((Long) row[1]).intValue());
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
    public List<Object[]> getMonthlyRevenueStatisticsLastYear() {
        String hqlByMonthRevenueLastYear = "SELECT MONTH(o.createdAt) AS month, SUM(o.totalPrice) AS monthlyRevenue " + // Thay 'orderDate' thành 'createdAt' và 'totalAmount' thành 'totalPrice'
                "FROM Order o " +
                "WHERE YEAR(o.createdAt) = YEAR(CURRENT_DATE) - 1 " + // Thay 'orderDate' thành 'createdAt'
                "GROUP BY MONTH(o.createdAt)"; // Thay 'orderDate' thành 'createdAt'
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(hqlByMonthRevenueLastYear, Object[].class);
            return query.list();
        }
    }

    public double getTotalRevenueCurrentMonth() {
        String hql = "SELECT SUM(o.totalPrice) FROM Order o " +
                "WHERE MONTH(o.createdAt) = MONTH(CURRENT_DATE) " +
                "AND YEAR(o.createdAt) = YEAR(CURRENT_DATE)";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Double> query = session.createQuery(hql, Double.class);
            return query.uniqueResult() != null ? query.uniqueResult() : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    public int getTotalUsers() {
        String hql = "SELECT COUNT(u) FROM User u";  // Truy vấn tổng số người dùng
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(hql, Long.class);
            return query.uniqueResult().intValue();  // Trả về tổng số người dùng
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getTotalRevenueLastYear() {
        String hql = "SELECT SUM(o.totalPrice) FROM Order o " +
                "WHERE YEAR(o.createdAt) = YEAR(CURRENT_DATE) - 1";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Double> query = session.createQuery(hql, Double.class);
            return query.uniqueResult() != null ? query.uniqueResult() : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public int getTotalProductSoldCurrentYear() {
        String hql = "SELECT SUM(oi.quantity) FROM OrderItem oi " +
                "WHERE YEAR(oi.order.createdAt) = YEAR(CURRENT_DATE)";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(hql, Long.class);
            return query.uniqueResult() != null ? query.uniqueResult().intValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}
