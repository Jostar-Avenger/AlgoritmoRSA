# AlgoritmoRSA
Progetto scolastico di Carnielli Denis 5A

Ho creato un algoritmo RSA in Java seguendo alcuni passagi:

Inizialmente ho enerato delle chiavi

Ho generato dei numeri primi casuali (p, q), calcolato n = p * q e phi = (p-1)(q-1).
Poi scelto un esponente pubblico e co-primo con phi e calcolato l'esponente privato d come inverso modulo di phi.

In seguito ho convertito il testo in chiaro in numeri, lo elevo a e modulo n per ottenere il testo cifrato.

Per decriptarlo ho elevato il testo cifrato a d modulo n, ottenendo il messaggio originale.

Ho pensato di usare uno switch per permettere all'utente di scegliere se inserire l'input da tastiera o se scriverlo su un file e successivamente criptarlo
Infine ho salvato automaticamente il testo decriptato in un file predefinito e stampandolo da console.

Ho aggiunto inoltre un sistema di log per registrare eventi importanti (come successo o errori) usando la libreria java.util.logging. Ho utilizzato i log per aiutarmi a monitorare le operazioni, come la generazione delle chiavi e la gestione di file.
