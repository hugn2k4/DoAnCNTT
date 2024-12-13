package com.laptop.service;

import com.laptop.models.Product;
import com.laptop.dao.ProductDAO;
import com.laptop.utils.Protector;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService extends ProductDAO {
    public ProductService() {
    }

    public int count() {
        try (Session session = getCurrentSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(id) FROM Product", Long.class)
                    .uniqueResult();
            return count != null ? count.intValue() : 0;
        }
    }

    public String getFirst(String twopartString) {
        return twopartString.contains("-") ? twopartString.split("-")[0] : "";
    }

    public String getLast(String twopartString) {
        return twopartString.contains("-") ? twopartString.split("-")[1] : "";
    }

    private int getMinPrice(String priceRange) {
        return Protector.of(() -> Integer.parseInt(getFirst(priceRange))).get(0);
    }

    private int getMaxPrice(String priceRange) {
        return Protector.of(() -> {
            String maxPriceString = getLast(priceRange);
            if (maxPriceString.equals("infinity")) {
                return Integer.MAX_VALUE;
            }
            return Integer.parseInt(maxPriceString);
        }).get(0);
    }

    public String filterByPublishers(List<String> publishers) {
        String publishersString = publishers.stream().map(p -> "'" + p + "'").collect(Collectors.joining(", "));
        return "p.publisher IN (" + publishersString + ")";
    }

    public String filterByPriceRanges(List<String> priceRanges) {
        // Kiểm tra danh sách đầu vào
        if (priceRanges == null || priceRanges.isEmpty()) {
            return ""; // Không thêm điều kiện lọc
        }
        // Chuỗi lọc
        StringBuilder filterString = new StringBuilder();

        for (int i = 0; i < priceRanges.size(); i++) {
            String range = priceRanges.get(i);
            String[] prices = range.split("-"); // Tách minPrice và maxPrice

            if (prices.length == 2) {
                try {
                    // Kiểm tra nếu max price là "infinity"
                    if (prices[1].equalsIgnoreCase("infinity")) {
                        int minPrice = (int) Double.parseDouble(prices[0]);
                        if (i > 0) {
                            filterString.append(" OR "); // Kết nối điều kiện bằng OR
                        }
                        filterString.append("p.price >= ").append(minPrice); // Chỉ lọc >= minPrice
                    } else {
                        // Parse min và max prices nếu không phải infinity
                        int minPrice = (int) Double.parseDouble(prices[0]);
                        int maxPrice = (int) Double.parseDouble(prices[1]);

                        if (i > 0) {
                            filterString.append(" OR "); // Kết nối điều kiện bằng OR
                        }
                        filterString.append("p.price >= ").append(minPrice)
                                .append(" AND p.price <= ").append(maxPrice); // Lọc theo min và max
                    }

                } catch (NumberFormatException e) {
                    System.err.println("Invalid price range format: " + range); // Ghi log lỗi
                }
            }
        }

        return filterString.length() > 0 ? filterString.toString() : "";
    }

    public String createFiltersQuery(List<String> filters) {
        return String.join(" AND ", filters);
    }

    public List<String> getRamsByCategoryId(long categoryId) {
        return getProductDAO().getDistinctRAMValues();
    }

    public List<String> getCpusByCategoryId(long categoryId) {
        return getProductDAO().getDistinctCPUsValues();
    }

    public List<String> getVgasByCategoryId(long categoryId) {
        return getProductDAO().getDistinctVGAValues();
    }

    public String filterByRams(List<String> rams) {
        String ramsString = rams.stream().map(r -> "'" + r + "'").collect(Collectors.joining(", "));
        return "p.RAM IN (" + ramsString + ")";
    }

    public String filterByCpus(List<String> cpus) {
        String cpusString = cpus.stream().map(c -> "'" + c + "'").collect(Collectors.joining(", "));
        return "p.CPU IN (" + cpusString + ")";
    }

    public String filterByVgas(List<String> vgas) {
        String vgasString = vgas.stream().map(v -> "'" + v + "'").collect(Collectors.joining(", "));
        return "p.VGA IN (" + vgasString + ")";
    }

    private ProductDAO getProductDAO() {
        return new ProductDAO();
    }

    // Lấy danh sách sản phẩm mới theo giới hạn và offset
    public List<Product> getNewProducts(int limit, int offset) {
        return getProductDAO().getByQuery("%%", 6, 0);
    }

    public List<Product> getBestSellingProducts(int limit, int offset) {
        return getProductDAO().getBestSellingProducts( limit, offset);
    }

    public int countProductsMax1Page() {
        try (Session session = getCurrentSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(id) FROM Product", Long.class)
                    .uniqueResult();
            int actualCount = count != null ? count.intValue() : 0;
            return Math.min(actualCount, 6); // Trả về tối đa là 6
        }
    }
}
