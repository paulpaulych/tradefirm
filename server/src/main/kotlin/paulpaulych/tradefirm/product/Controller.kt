package paulpaulych.tradefirm.product

import org.springframework.web.bind.annotation.*
import simpleorm.core.delete
import simpleorm.core.save
import simpleorm.core.query

@RestController
@RequestMapping("/product")
class Controller{

//    @GetMapping
//    fun get(@RequestParam id: Long): Product{
//        return Product::class.findById(id)
//                ?: error("data not found")
//    }

    @PostMapping
    fun post(@RequestBody product: Product): Product{
        return save(product)
    }

    @DeleteMapping
    fun delete(@RequestParam id: Long){
        return Product::class.delete(id)
    }

    @GetMapping
    fun getByName(@RequestParam name: String): List<Product>{
        return Product::class.query("select * from product where product_name = ?", listOf(name))
    }

}