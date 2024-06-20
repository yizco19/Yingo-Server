package cm.zy.controller;

import cm.zy.pojo.PageBean;
import cm.zy.pojo.Product;
import cm.zy.pojo.Result;
import cm.zy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Clase controlador para gestionar las operaciones de productos.
 * Autor: Zhi Yang
 * Fecha de Creación: 25/03/2024
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    /**
     * Agrega un nuevo producto.
     *
     * @param product el producto a agregar
     * @return un Result indicando éxito o error
     */
    @PostMapping
    public Result add(@RequestBody Product product){
        productService.add(product);
        return Result.success();
    }

    /**
     * Obtiene una lista paginada de productos filtrada por categoría y/o nombre del producto.
     *
     * @param pageNum el número de página
     * @param pageSize el tamaño de la página
     * @param categoryId el ID de la categoría (opcional)
     * @param productName el nombre del producto (opcional)
     * @return un Result que contiene la lista de productos si tiene éxito, o un mensaje de error si falla
     */
    @GetMapping
    public Result<PageBean<Product>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String productName
    ){
        PageBean<Product> pb = productService.list(pageNum,pageSize,categoryId,productName);
        return Result.success(pb);
    }
    /**
     * Obtiene una lista de todos los productos.
     *
     * @return un Result que contiene la lista de todos los productos disponibles si tiene éxito, o un mensaje de error si falla
     */
    @GetMapping("/list")
    public Result<List<Product>> list(){
        List<Product> list = productService.list();
        return Result.success(list);
    }
    /**
     * Obtiene una lista de todos los productos.
     *
     * @return un Result que contiene la lista de todos los productos disponibles si tiene éxito, o un mensaje de error si falla
     */
    @GetMapping("/detail")
    public Result<Product> detail(@RequestParam Integer id){

        Product article = productService.detail(id);
        return Result.success(article);
    }
    /**
     * Elimina un producto por su ID.
     *
     * @param id el ID del producto a eliminar
     * @return un Result indicando éxito o error
     */
    @DeleteMapping
    public Result delete(@RequestParam Integer id){
        productService.delete(id);
        return Result.success();
    }
    /**
     * Actualiza un producto existente.
     *
     * @param product el producto actualizado
     * @return un Result indicando éxito o error
     */
    @PutMapping("/update")
    public Result update(@RequestBody Product product){

        productService.update(product);
        return Result.success();
    }
    /**
     * Actualiza la visibilidad de un producto.
     *
     * @param id el ID del producto
     * @param visible true si el producto debe ser visible, false si no
     * @return un Result indicando éxito o error
     */
    @PutMapping("/visibility")
    public Result updateProductVisibility(@RequestParam int id ,@RequestParam boolean visible){
        int visibility = visible ? 1 : 0;
        productService.updateProductVisibility(id,visibility);
        return Result.success();
    }

}
