/* Laboratorio 6 - Microprocesadores
 * Nombre: José Rodrigo Barrera García 
 * Carnet: 20807
 */
#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#define MAXIMO 100
int main(int argc, char *argv[]) {

    int suma = 0;
    #pragma omp parallel for
    for(int i = 0 ; i <= MAXIMO ; i++){
	    suma = suma + i;
    }

    printf("El resultado de la suma es: %d\n", suma);

  return(0);
}