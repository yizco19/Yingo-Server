package cm.zy.controller;

import cm.zy.pojo.Category;
import cm.zy.pojo.Result;
import cm.zy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase controlador para gestionar las operaciones de categorías.
 * Autor: Zhi Yang
 * Fecha de Creación: 25/03/2024
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * Agrega una nueva categoría.
     *
     * @param category la categoría a agregar
     * @return un Result indicando éxito
     */
    @PostMapping
    public Result add(@RequestBody Category category){
        categoryService.add(category);
        return Result.success();
    }

    /**
     * Lista todas las categorías.
     *
     * @return un Result que contiene la lista de categorías
     */
    @GetMapping
    public Result<List<Category>> list(){
        List<Category> list = categoryService.list();
        return Result.success(list);
    }

    /**
     * Actualiza una categoría existente.
     *
     * @param category la categoría a actualizar
     * @return un Result indicando éxito
     */
    @PutMapping
    public Result update(@RequestBody Category category){
        categoryService.update(category);
        return Result.success();
    }

    /**
     * Elimina una categoría por su ID.
     *
     * @param id el ID de la categoría a eliminar
     * @return un Result indicando éxito
     */
    @DeleteMapping
    public Result delete(@RequestParam Integer id){
        categoryService.delete(id);
        return Result.success();
    }
    /**
     * Verifica si hay productos asociados a una categoría.
     *
     * @param id el ID de la categoría a verificar
     * @return un Result indicando éxito si no hay productos asociados, o un mensaje de error si hay productos asociados
     */
    @GetMapping("/checkProductsAssociated")
    public Result checkProductsAssociated(@RequestParam Integer id) {
        boolean associated = categoryService.checkProductsAssociated(id);
        if (associated) {
            return Result.success();
        }
        return Result.error("La categoría no puede ser eliminada porque tiene productos asociados");
    }
}
