PIGCOIN
=======

> Vamos a construir un prototipo de nuestra criptomoneda Pigcoin que permite gestionar mediante un blockchain la transferencia de monedas entre el alumnado y profesorado propietarios/as de wallets (o monederos).

* La cadena de bloques o _blockchain_ está compuesta por las transacciones que envían las _wallet_ o monederos entre sí, una detrás de otra. Cada entrada en el _blockchain_ es una transacción.
* Una _wallet_ es un monedero. Se identifica por su par clave privada y clave pública. Pertenece al usuario/a que conoce su clave privada. La clave pública es la dirección de la _wallet_ a la que se envían (o donde se reciben) los pigcoins.
* Cuando un usuario/a envía pigcoins, los envía a la dirección o clave pública de la _wallet_ de destino. Firma la transacción con su clave privada.
* El _blockchain_ debe verificar que una transacción es auténtica antes de añadirla a la cadena de bloques. Verifica la firma de la transacción mediante la clave pública de la wallet que envía los pigcoins.
* El _blockchain_ debe verificar que los pigcoins de la transacción no han sido gastados antes, es decir, que no existen en el _blockchain_ transacciones que utilicen los pigcoins que se pretenden usar ahora. 
* Una transacción es como un billete de pigcoins. Para gastarlo, ese billete debe habértelo entregado alguien y no puedes haberlo gastado antes. Cuando envías pigcoins, utilizas la transacción en la que los recibiste y dicha transacción NO puede volverse a utilizar (se destruye esa moneda). **Cada transacción sólo puede ser utilizada una vez. En el _blockchain_ no puede haber dos transacciones que provengan de la misma transacción**.
* Los pigcoins son transacciones indivisibles. Si quieres enviar 8 _pgc_ (pigcoins) y sólo dispones de un billete /transacción recibida de 10 _pgc_, debes enviar dos transacciones: una de 8 _pgc_ para el destinatario/a y 2 _pgc_ para tí mismo/a. A esto se la llama _change address_.

## Prepara el proyecto

 1. Crea un nuevo repo en tu cuenta en **Github**.
 2. Crea un nuevo directorio en tu equipo y **clona el repositorio** de Github.
 3. Abre VSCode /Eclipse /Netbeans y **establece como workspace** el directorio que has clonado.
 4. Crea un proyecto **Maven** que incluya tu **nombre y apellidos**.
 5. Pon el proyecto en seguimiento en **Git** y configura `.gitignore`.
 5. Copia y pega la función principal `App.java`. Utilízala como guía en el proceso TDD. **No puedes modificar su código**, pero sí puedes comentar aquellas partes que aun no hayas implementado.
 6. Añade al proyecto la clase `GenSig.java` que ofrece varias utilidades que emplearás.
 7. Completa las clases que aquí se indican **implementando los casos test que necesites**. Practica **TDD**.
 8. Realiza `commits` como mínimo cada vez que termines una historia de usuario. 

## Cómo entregar el código

 1. Desde Eclipse exporta el proyecto a un fichero.
 2. En VSCode, comprime la carpeta del proyecto.
 3. Envíame el archivo por correo electrónico.
 4. **Realiza commits periódicamente** mientras avanzas en el desarrollo de la aplicación.
 5. Realiza un `push` al repo remoto en GitHub **SOLO cuando hayas terminado el proyecto**.

## Salida de la aplicación

Intenta que la salida del programa sea lo más parecida posible a la imagen que se proporciona.

## Historias de usuario

Las hitorias de usuario están enunciadas en el script principal `App.java`

Crea un documento donde escribas las historias de usuario correspondientes a los hitos del proceso.

![01 Monty Python pig](http://78.media.tumblr.com/tumblr_mej14zLYde1qe1l45o1_500.gif)

## Diagrama de clases UML

Realiza a mano alzada -o con la aplicación que prefieras- un pequeño diagrama de clases UML que muestre la relación entre las clases que has construido, con su interfaz pública y privada.

## Código

### Clase `Transaction`

Una **transacción** es una transferencia de pigcoins entre dos **wallets**.

#### Atributos

* `hash` es el identificador de la transacción en el **Blockchain**.
* `prev_hash` es el identificador de la transacción previa donde se originaron los los pigcoins que ahora pretendes gastar.
* `PublicKey pKey_sender` es la **dirección pública** de la **wallet** desde la que se envían los pigcoins. Para nosotros es también la **clave pública** de la Wallet.
* `PublicKey pKey_recipient` es la **dirección pública** de la **wallet** a la que se envían los pigcoins (el monedero que los recibe). Para nosotros es también la **clave pública** de la Wallet.
* `pigcoins` es la cantidad de pigcoins que se envían.
* `message` es el mensaje que acompaña a la transacción y que escribe el usuario/a que envía los pigcoins.
* `byte[] signature` es el mensaje `message` firmado con la clave privada del usuario/a que genera (envía) la transacción.

#### Métodos

Esta clase no tiene lógica, sólo `getters` y `setters`. Si programas las historias de usuario indicadas en  `App.java` construirás los getters y setters necesarios.

* `toString()` devuelve la representación en `String` de un objeto de la clase `Transaction`.

### Clase `Wallet`

Esta clase representa una wallet o monedero donde el usuario/a visualiza los pigcoins que "posee" y desde donde los envia o recibe.

#### Atributos

* `PublicKey address` es la **dirección** pública o **clave pública** de la wallet, que se usa para enviar y recibir pigcoins.
* `PrivateKey sKey` es la **clave privada** de la wallet. **Sólo es conocida por el propietario de la wallet**. Se usa para firmar los mensajes de las transacciones que se envían desde esta wallet, para que el _blockchain_ pueda validar que son auténticas. No se envía nunca ni se hace pública.
* `total_input` el total de pigcoins que han sido recibidos en esta wallet, es decir, que han sido enviados a esta dirección pública desde el comienzo del _blockchain_.
* `total_output` el total de pigcoins que se han enviado desde esta wallet, es decir, que se han enviado desde esta dirección pública desde el comienzo del _blockchain_.
* `balance` son los pigcoins que "posee" este usuario. Son los pigcoins recibidos menos los enviados. No puede ser negativo.
* `inputTransactions` son las transacciones que tienen como destino esta dirección pública o wallet.
* `outputTransactions` son las transacciones que tiene como origen esta dirección pública o wallet (las transacciones que se han enviado desde esta.

#### Métodos

* `generateKeyPair()` genera el par clave privada - clave pública para la wallet utilizando la clase `GenSig`. Es el propio usuario/a el que genera el par. No depende de ninguna autoridad externa.
* `setAddress(PublicKey pKey)` establece la clave pública o dirección pública de la wallet.
* `setSK(PrivateKey sKey)` establece la clave privada de la wallet.
* `getAddress()` devuelve la dirección pública de la wallet.
* `getSKey()` devuelve la clave privada de la wallet.
* `loadCoins(bChain)` carga en la wallet el total de pigcoins recibidos y enviados desde esta wallet o dirección pública.
* `loadInputTransactions(bChain)` carga en la wallet las transacciones del _blockchain_ que tienen como destino esta dirección pública.
* `loadOutputTransactions(bChain)` carga en la wallet las transacciones del _blockchain_ que tienen como origen esta dirección pública.
* `sendCoins(pKey_recipient, coins, message, bChain)` envia la cantidad de pigcoins indicada a la dirección (clave pública) indicada, con un mensaje. Es decir, intenta añadir al _blockchain_ una nueva transacción.
* `byte[] signTransaction(message)` firma el mensaje que acompaña transacción. El _blockchain_ verificará que esta firma es auténtica antes de aceptar la transacción y añadirla a la cadena de bloques.
* `toString()` devuelve la representación en `String` de un objeto de la clase `Wallet`.
* `collectCoins(pigcoins)` recorre las transacciones recibidas y enviadas a la wallet para recolectar el número de pigcoins que se quieren enviar en la transacción. La lógica es la siguiente:
1. Primero descarta las transacciones que hemos recibido que ya se han utilizado para enviar pigcoins en transacciones anteriores. Son las transacciones que ya se han enviado desde tu wallet. Los pigcoins de una transacción recibida sólo pueden utilizarse una vez. Les llamamos transacciones consumidas.
2. Si el total de pigcoins a enviar es **igual** a una transacción recibida que no ha sido consumida, puedes utilizarla para enviar sus pigcoins.
3. Si el total de pigcoins a enviar es **menor** que una transacción recibida que no ha sido consumida, genera dos transacciones (_change address_): una para el destinatario/a y otra para ti. Ejemplo: si quieres enviar 8 _pgc_ y sólo dispones de una transacción recibida de 10 _pgc_, enviarás dos transacciones: una de 8 _pgc_ para el destinatario/a y 2 _pgc_ para tí mismo/a. A esto se la llama _change address_.
4. Si el total de pigcoins a enviar es **mayor** que una transacción recibida que no ha sido consumida, puedes enviar sus pigcoins, pero necesitas encontrar otras transacciones hasta completar el total de pigcoins a enviar.

### Clase `BlockChain`

#### Atributos

* `blockChain` el _blockchain_ lo componen las transacciones de pigcoins que han realizado los propietarios de las wallets. 

#### Métodos

* `addOrigin(transaction)` añade al _blockchain_ una transacción que **crea moneda**. No se trata de transacciones entre wallets. Es el sistema el que genera pigcoins y se los envía a una wallet.
* `processTransactions(pKey_sender, pKey_recipient, consumedCoins, message signedTransaction)` procesa las transacciones que las wallet envían. Añade las transacciones al _blockchain_ cuando se ha verificado que su firma es auténtica y que los pigcoins que utiliza no se han gastado antes (_double spend_).
* `isConsumedCoinValid(consumedCoins)` verifica que los pigcoins que se intentan enviar en la transacción no se han gastado antes, es decir, que provienen de una transacción que no ha sido utilizada ya.
* `isSignatureValid(pKey_sender, message, signedTransaction)` verifica que la firma de la transacción es válida, utilizando la clave pública de la wallet que envía los pigcoins.
* `createTransaction(pKey_sender, pKey_recipient, consumedCoins, message, signedTransaction)` añade al _blockchain_ una transacción cuando todo es correcto.
* `loadInputTransactions(PublicKey address)` carga en la _wallet_ las transacciones que tienen como destino esa dirección o clave pública.
* `loadOutputTransactions(PublicKey address)` carga en la _wallet_ las transacciones que tienen como origen esa dirección o clave pública.
* `loadWallet(PublicKey address)` carga en la wallet los pigcoins envidos y recibidos en esa dirección.
* `summarize()` visualiza el _blockchain_
* `summarize(index)` visualiza una transacción del _blockchain_ situada en la posición `index`

### Clase `GenSig`

Esta clase contiene las utilidades para generar el par de clave pública y privada, para firmar mensajes y para verificar que la firma de un mensaje es auténtica.

**NO puedes modificar su código**

La documentación de cada utilidad se encuentra en la propia clase.