package cm.zy.pojo;

import lombok.Data;

@Data
public class Category {
    private Integer id;                // ID principal
    private String categoryName;       // Nombre de la categoría
    private String categoryAlias;      // Alias de la categoría
}
