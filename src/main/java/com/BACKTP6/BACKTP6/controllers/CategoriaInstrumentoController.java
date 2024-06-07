package com.BACKTP6.BACKTP6.controllers;

import com.BACKTP6.BACKTP6.entities.CategoriaInstrumento;
import com.BACKTP6.BACKTP6.entities.Instrumento;
import com.BACKTP6.BACKTP6.repositories.CategoriaInstrumentoRepository;
import com.BACKTP6.BACKTP6.services.CategoriaInstrumentoServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/categorias")
public class CategoriaInstrumentoController {
    @Autowired
    private CategoriaInstrumentoServiceImplementation categoriaService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCategories(){
        try{

            return ResponseEntity.status(HttpStatus.OK).body(categoriaService.findAll());

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error");
        }

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        try{

            return ResponseEntity.status(HttpStatus.OK).body(categoriaService.findById(id));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error");
        }

    }

    @PostMapping("/create")
    public ResponseEntity<?> createCateogory(@RequestBody CategoriaInstrumento category) {
        try {
            return ResponseEntity.ok(categoriaService.create(category));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating the Category");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoriaInstrumento category) {
        try {
            category.setId(id);
            return ResponseEntity.ok(categoriaService.update(id, category));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoriaService.delete(id);
            return ResponseEntity.ok().body("Category deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }
    }

    @PostMapping("/guardarCategorias")
    public ResponseEntity<List<CategoriaInstrumento>> addCategorias(@RequestBody List<CategoriaInstrumento> categorias) {
        List<CategoriaInstrumento> categoriasGuardadas = categoriaService.guardarCategorias(categorias);
        return ResponseEntity.ok(categoriasGuardadas);
    }
}
