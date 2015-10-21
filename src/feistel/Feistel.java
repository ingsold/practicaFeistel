/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feistel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jhercules
 */
public class Feistel {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    public String textoplano(String texto,String clave,boolean cifrado){
        String cadena="";
        ArrayList<String> bloques=dividirTexto(texto);
        ArrayList<Integer> claves=new ArrayList();
        ArrayList<Integer> Izquierda=new ArrayList();
        ArrayList<Integer> Derecha=new ArrayList();
        ArrayList<Integer> nuevoArreglo = new ArrayList();
         for (int i = 0; i < 8; i++) {
            claves.add((int)clave.charAt(i));
        }
        //System.out.println("numero de bloques  "+ bloques.size());
        for (int i = 0; i < bloques.size(); i++) {
             String bloque=bloques.get(i);
            for (int j = 0; j < bloque.length(); j++) {
                if(j<8){
                    Izquierda.add((int)texto.charAt(j));
                }else{
                    Derecha.add((int)texto.charAt(j));
                }
            }
            if(cifrado){
                nuevoArreglo.addAll(cifrar(Izquierda,Derecha,claves,9,cifrado));
             }else {
                nuevoArreglo.addAll(cifrar(Derecha,Izquierda,claves,9,cifrado));
            }
            Izquierda.clear();
            Derecha.clear();
            
        }
        
        for (int i = 0; i < nuevoArreglo.size(); i++) {
            
            cadena=cadena+(char)Integer.parseInt(nuevoArreglo.get(i).toString());
        }
 
       return cadena;
    }
    
    
    public static ArrayList<String> dividirTexto(String text) {
        // Give the list the right capacity to start with. You could use an array
        // instead if you wanted.
        ArrayList<String> ret = new ArrayList<>((text.length() + 16 - 1) / 16);
        for (int start = 0; start < text.length(); start += 16) {
            ret.add(text.substring(start, Math.min(text.length(), start + 16)));
        }
        return ret;
    }
    public ArrayList<Integer> funcion(ArrayList<Integer> Izquierda,ArrayList<Integer> Derecha,ArrayList<Integer> clave,boolean cifrado){
        ArrayList<Integer> Resultado=new ArrayList();
        int temporal=0;
        int temporal2=0;
        
        for (int i = 0; i < 8; i++) {
          //-  System.out.println("Derecha: "+Derecha);
          //  System.out.println("IZQUIERDA: "+Izquierda);
          //  System.out.println("clave: "+clave);
            temporal=(int)Derecha.get(i)^(int)clave.get(i);
            temporal2=temporal^(int)Izquierda.get(i);
       //     System.out.println("TEMPORAL1 "+temporal);
       //     System.out.println("TEMPORAL2 "+temporal2);
            Resultado.add(temporal2);
          //  clave=mueveclave(clave,cifrado);
        }
        return  Resultado;
    }
   
    
    public ArrayList<Integer> cifrar(ArrayList<Integer> Izquierda,ArrayList<Integer> Derecha,ArrayList<Integer> clave,int iteraciones,boolean cifra){
        ArrayList<Integer> temporal=new ArrayList();
        
        for (int i = 0; i < iteraciones; i++) {
            //
       System.out.println("CLAVE FINAL "+i+" "+clave);
      //      System.out.println("LLAMA CIFRADO FIN");  
       System.out.println("DERECHA "+i+" "+Derecha);
       System.out.println("IZQUIERDA "+i+" "+Izquierda);
            temporal=funcion(Izquierda,Derecha,clave,cifra);
            Izquierda=Derecha;
            Derecha=temporal;
      //      System.out.println("LLAMA CIFRADO INICIO");  
            clave=mueveclave(clave,cifra);
        String cadena="";
        for (int j = 0; j < clave.size(); j++) {
            
            cadena=cadena+(char)Integer.parseInt(clave.get(j).toString());
        }

        }
       
     //clavefinal.addAll(clave);
        if(cifra){
           Izquierda.addAll(Derecha); 
           return Izquierda;
        }else {
            Derecha.addAll(Izquierda);
            return Derecha;
        }
      
      
    }
    

    
    
    public ArrayList<Integer> mueveclave(ArrayList<Integer> clave,boolean rotacion){
        ArrayList<Integer> binario=new ArrayList();
     //   System.out.println("LA CLAVE "+ clave   );
        for (int i = 0; i < clave.size(); i++) {
            String NumeroBinario=agregarCeros(clave.get(i));
        //    System.out.println("LONGITUD NUMERO BINARIO: "+NumeroBinario.length());
             for (int j = 0; j < NumeroBinario.length(); j++) {
                    binario.add(new Integer(NumeroBinario.substring(j,j+1)));
           }            
        }
       // System.out.println("TAMAÑO DEL BINARIO " +binario.size());
       // System.out.println("MUEVE LA CLAVE");
        clave.clear();
        clave.addAll(rotacion(binario,rotacion));
       // System.out.println("CLAVE:" +binario.size());

        return clave;
    }
    public ArrayList<Integer> rotacion(ArrayList<Integer> binario,boolean rotacion){
        ArrayList<Integer> decimal=new ArrayList();
        ArrayList<Integer> movimiento=new ArrayList();
           String cadena="";
         //System.out.println("LLAMA ROTAR CLAVE");
        if(rotacion){
            for (int i = 0; i < binario.size()-1; i++) {
                movimiento.add(binario.get(i+1));
             }
            movimiento.add(binario.get(0));
        }else{
             movimiento.add(0);
             for (int i = 1; i < binario.size(); i++) {
                movimiento.add(binario.get(i-1));
             }
             movimiento.set(0, binario.get(binario.size()-1));     
        }
      //  System.out.println("MOVIMIENTO TAMAÑO "+movimiento.size());
        for(int i = 0; i < movimiento.size(); i++) {
            if(cadena.length()>7){
                decimal.add(Integer.parseInt(cadena, 2));
                cadena=movimiento.get(i).toString();
                //System.out.println("cadena: "+cadena);
            }else{
                cadena=cadena+movimiento.get(i).toString();
                //System.out.println("cadena: "+cadena);
            }
       }
        decimal.add(Integer.parseInt(cadena, 2));
        //System.out.println("DECIMAL TAMAÑO "+decimal.size());
      // System.out.println("binario: "+binario+" tamaño: "+binario.size());
      // System.out.println("movimiento: "+movimiento+" tamaño: "+movimiento.size());
      // System.out.println("decimal: "+decimal+" tamaño: "+decimal.size());
        return decimal;
    }
    
     public String agregarCeros(int i) {
        String original = Integer.toBinaryString(i);
        if (original.length() < 8) {
            char c = '0';
            int number = 8 - Integer.toBinaryString(i).length();
            char[] repeat = new char[number];
            Arrays.fill(repeat, c);
            original = new String(repeat) + original;
            //System.out.println("ORGINAL " + original);
        }
        return original;
    }
    
    
    
}
