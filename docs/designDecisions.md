# Project-One
## Design decisions

// TODO : Michael : Replace BS Below

### MVC
To implement our MVC application, we first created the models
#### Models

They are located in the `models` diretory, and are mostly boilerplate code.

For that reason, we made ample use of Lombok, an annotation pre-processor to automatically generate our boilerplate code for us.

#### Views
They are located in the `webapp` directory

They are simply jsps, rendered based on what they received from the controllers.

#### Controllers
They are located in the `presentation` directory

They are simple servlets or webfilters.

They link the data they obtain from the integration layer, and then proceed to send it to the jsp's rendering engine, which is then forwarded to the user.

#### Access control

We implemented basic levels of acess control in the AuthFilter, located in the `presentation/filter` directory.

We implemented it in such a way that we capture all URLs with the filter, and sequentially check if the user can access them, and if not, redirect them where appropriate (index, or login page)

#### Integration Layer

For our integration layer, we decided to duplicate most methods, where appropriate, to allow both the use of primitive values or objects to infulence the model data.

We also implemented private generic querries, which where specialised in the public methods, to remove code duplication as much as possible.

Finally, we decided not to implement many checks in the input validity, and simply let the SQL querry fail if the input is invalid, to reduce code complexity.

#### Buisness Layer

In this layer, we only implemented a utils class, who's job is simply to verify that the required parameters are provided, and if not, to redirect the output to a specific view, while copying a subset of the parameters to check for, as a means to not lose the user input.

---
[Return to the main readme](https://github.com/capito27/Teaching-HEIGVD-AMT-2019-Project-One/blob/master/README.md)
