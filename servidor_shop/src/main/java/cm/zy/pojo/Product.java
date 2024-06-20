package cm.zy.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class Product {
    @NotNull
    private Integer id;             // Identificador principal (ID)
    private Double price;           // Precio
    @NotNull
    @NotEmpty
    private int categoryId;         // Identificador de categoría
    @NotNull
    private String name;            // Nombre
    private String description;     // Descripción
    private int stock;              // Cantidad disponible
    @URL
    private String productPic;       // Imagen de producto
    @NotNull
    private boolean visible;        // Visibilidad
    private Integer offerId;        // Identificador de oferta
}
