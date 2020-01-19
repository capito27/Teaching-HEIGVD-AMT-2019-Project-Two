## Project-One

## Generalitées 

Nous avons profité des interfaces API Spring générées par OpenAPIGenerator afin d'avoir le squelètte des controllers API, et simplement implémenter les methodes necessaires de la spec.

Nous avons profité des Repository JPA (En particulier des CrudRepository) pour implémenter les communications avec la database.
Nous avons profité de la reflexion de Hibernate afin de pouvoir générer dynamiquement un maximum de requetes sans devoir écrire manuellement le code SQL associé à ces requètes.

Nous avons profités des annotations Lombok (Principalement @Data) pour générer authomatiquement le boilerplate associé à nos entitées

Nous avons également profité des annotations pour la persistence, afin de définire les éventuels noms de colone non-standards de notre base de donnée

Nous avons également profité des filtres servlet afin de définir les CORS (Qui sont réstés à leur valeurs par défaut, qui ne sont pas secure.)

## Auth

Nous avons profité des WebFilter de spring (plus particulièrement des OncePerRequestFilter) afin de forçer le controler d'authentification de l'utilisateur, et de transmettre les données associées au jeton JWT d'identification aux endpoints en ayant basoin.

Nous avons également stocké le mail, l'ID et le statut (admin ou non) dans ce jeton, qui seront utilisée par les deux API.

## Match API

Nous avons profités des annotations JPA afin de faciliter la représentation des entitées et de leurs liasions ( avec les liens "OneToMany" et "ManyToOne")


---
[Return to the main readme](https://github.com/capito27/Teaching-HEIGVD-AMT-2019-Project-Two/blob/master/README.md)
