package cm.zy.controller;

import cm.zy.pojo.Offer;
import cm.zy.pojo.Result;
import cm.zy.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase controlador para gestionar las operaciones de ofertas.
 * Autor: Zhi Yang
 * Fecha de Creación: 30/04/2024
 */
@RestController
@RequestMapping("/offer")
public class OfferController {

    @Autowired
    private OfferService offerService;
    /**
     * Obtiene la lista de todas las ofertas.
     *
     * @return un Result que contiene la lista de ofertas si tiene éxito, o un mensaje de error si falla
     */
    @GetMapping("/list")
    public Result<List<Offer>> list(){

        List<Offer> list = offerService.list();
        return Result.success(list);
    }
    /**
     * Obtiene una oferta por su ID.
     *
     * @param id el ID de la oferta
     * @return un Result que contiene la oferta si tiene éxito, o un mensaje de error si falla
     */
    @GetMapping("/getOfferById")
    public Result<Offer> getOfferById(@RequestParam Integer id){
        Offer offer = offerService.getOfferById(id);
        return Result.success(offer);
    }
    /**
     * Agrega una nueva oferta.
     *
     * @param offer la oferta a agregar
     * @return un Result indicando éxito o error
     */
    @PostMapping
    public Result add(@RequestBody Offer offer){

        offerService.add(offer);
        return Result.success();
    }
    /**
     * Actualiza una oferta existente.
     *
     * @param offer la oferta a actualizar
     * @return un Result indicando éxito o error
     */
    @PutMapping("/update")
    public Result update(@RequestBody Offer offer){
        offerService.update(offer);
        return Result.success();
    }
    /**
     * Elimina una oferta por su ID.
     *
     * @param id el ID de la oferta a eliminar
     * @return un Result indicando éxito o error
     */
    @DeleteMapping
    public Result delete(@RequestParam Integer id){
        offerService.delete(id);
        return Result.success();
    }

}