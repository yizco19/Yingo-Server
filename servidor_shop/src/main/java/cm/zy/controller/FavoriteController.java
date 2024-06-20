package cm.zy.controller;

import cm.zy.pojo.Favorite;
import cm.zy.pojo.Result;
import cm.zy.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase controlador para gestionar las operaciones de favoritos.
 * Autor: Zhi Yang
 * Fecha de Creación: 20/05/2024
 */
@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;
    /**
     * Agrega un nuevo favorito.
     *
     * @param favorite el favorito a agregar
     * @return un Result indicando éxito
     */
    @PostMapping
    public Result add(@RequestBody Favorite favorite){
        favoriteService.add(favorite);
        return Result.success();
    }
    /**
     * Lista todos los favoritos.
     *
     * @return un Result que contiene la lista de favoritos
     */
    @GetMapping
    public Result<List<Favorite>> list(){
        List<Favorite> list = favoriteService.list();
        return Result.success(list);
    }
    /**
     * Elimina un favorito por ID de producto.
     *
     * @param productId el ID del producto a eliminar de favoritos
     * @return un Result indicando éxito
     */
    @DeleteMapping
    public Result delete(@RequestParam Integer productId){
        favoriteService.delete(productId);
        return Result.success();
    }

    /**
     * Elimina todos los favoritos.
     *
     * @return un Result indicando éxito
     */
    @DeleteMapping("/all")
    public Result deleteAll(){
        favoriteService.deleteAll();
        return Result.success();
    }
}
