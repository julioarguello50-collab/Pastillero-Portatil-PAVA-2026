/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author argue
 * @param <T>
 */
public interface CRUD<T> {
    public boolean registrar(T objeto);
    
    public ArrayList listar();
    
    public T buscarId(int id);
    
    public boolean update(T objeto);
    
    public boolean delete(int id);
}
