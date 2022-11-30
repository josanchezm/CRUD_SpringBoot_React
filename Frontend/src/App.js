import React, { Component } from 'react';
import './App.css';
import api from './api/posts';
import "bootstrap/dist/css/bootstrap.min.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEdit, faTrashAlt, faTrash } from '@fortawesome/free-solid-svg-icons';
import { Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';


class App extends Component {
  // Creando el estado con la data a mostrar, los estados de los modal y la estructura del formulario para ingresar contactos
  state={
    data:[],
    filteredData:[],
    modalInsertar: false,
    modalEliminar: false,
    modalEliminarLogico: false,
    form:{
      id: '',
      nombre: '',
      apellido: '',
      telefono: '',
      email: '',
      fecha_nac: '',
      estado: '',
      tipoModal: '', 
    }
  }

  // Creación de las peticiones 
  peticionGet = async () => {
    await api.get('').then(response=>{
      this.setState({data: response.data.data});
      // Filtrando los contactos con estado true para aplicar el borrado logico 
      this.setState({filteredData: this.state.data.filter(contacto => contacto.estado === true)});
      console.log(response.data.data);
    }).catch(error=>{
      console.log(error.message);
    })
  }

  peticionPost = async () => {
    delete this.state.form.id; 
    await api.post('api/v1/contact',this.state.form).then(response=>{
      this.modalInsertar();
      this.peticionGet();
    }).catch(error=>{
      console.log(error.message);
    })
  }

  peticionPut = () => {
    api.put('api/v1/contact/'+this.state.form.id, this.state.form).then(response=>{
      this.modalInsertar();
      this.peticionGet();
      console.log(response);
    }).catch(error=>{
      console.log(error.message);
    })
  }

  peticionDelete = () => {
    api.delete('api/v1/contact/'+this.state.form.id).then(response=>{
      this.setState({modalEliminar: false});
      this.peticionGet();
      console.log(response);
    }).catch(error=>{
      console.log(error.message);
    })
  }

  peticionDeleteLogico = () => {
    api.put('api/v1/contact/'+this.state.form.id, {
      id: this.state.form.id,
      nombre: this.state.form.nombre,
      apellido: this.state.form.apellido,
      telefono: this.state.form.telefono,
      email: this.state.form.email,
      fecha_nac: this.state.form.fecha_nac,
      estado: false,
    }).then(response=>{
      this.setState({modalEliminarLogico: false})
      this.peticionGet();
      console.log(response);
    }).catch(error=>{
      console.log(error.message);
    })
  }

  // Funcion para el modal de agregar contacto 
  modalInsertar = () => {
     this.setState({modalInsertar: !this.state.modalInsertar});
  }

  // Seleccionar contacto para actualizar
  seleccionarContacto = (contacto) => {
    this.setState({
      tipoModal: 'actualizar',
      form: {
        id: contacto.id,
        nombre: contacto.nombre,
        apellido: contacto.apellido,
        telefono: contacto.telefono,
        email: contacto.email,
        fecha_nac: contacto.fecha_nac,
        estado: contacto.estado,
      }
    })
  }

  // Funcion para capturar la informacion de los inputs 
  handleChange = async e => {
    e.persist();
    await this.setState({
      form:{
        ...this.state.form,
        [e.target.name]: e.target.value
      }
    });
    console.log(this.state.form);
  }

  // Ejecutando la peticion Get
  componentDidMount() {
    this.peticionGet();
  }
  
  render(){
    const {form} = this.state;
    return (
      <div className="App">
        <h2 className='my-4'>Listado de contactos telefónicos</h2>
        <button className="btn btn-success" onClick={()=>{this.setState({form: null, tipoModal: 'insertar'}); this.modalInsertar()}}>Agregar Contacto</button>
        <br /><br />
        {/* Tabla para mostrar los contactos  */}
        <table className="table ">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Apellido</th>
              <th>Teléfono</th>
              <th>Email</th>
              <th>Fecha Nacimiento</th>
              <th>Acción</th>
            </tr>
          </thead>
          <tbody>
            {/* Funcion map para recorrer el array de contactos y mostrarlos en tabla */}
            {this.state.data.map(contacto=>{
              return(
                <tr>
                  <td>{contacto.id}</td>
                  <td>{contacto.nombre}</td>
                  <td>{contacto.apellido}</td>
                  <td>{contacto.telefono}</td>
                  <td>{contacto.email}</td>
                  <td>{contacto.fecha_nac}</td>
                  <td>
                    {/* Botones de editar y borrar contactos */}
                    <button className="btn btn-primary" onClick={()=>{this.seleccionarContacto(contacto); this.modalInsertar()}}><FontAwesomeIcon icon={faEdit}/></button>
                    {"   "}
                    <button className="btn btn-danger" onClick={()=>{this.seleccionarContacto(contacto); this.setState({modalEliminar: true})}}><FontAwesomeIcon icon={faTrashAlt}/></button>
                    {"   "}
                    <button className="btn btn-danger" onClick={()=>{this.seleccionarContacto(contacto); this.setState({modalEliminarLogico: true})}}><FontAwesomeIcon icon={faTrash}/></button>
                  </td>
                </tr>
              )
            })}
          </tbody>
        </table>
        <h2 className='my-4'>Listado de contactos telefónicos (borrado lógico)</h2>
        {/* Tabla para mostrar los contactos con estado true */}
        <table className="table ">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Apellido</th>
              <th>Teléfono</th>
              <th>Email</th>
              <th>Fecha Nacimiento</th>
              <th>Acción</th>
            </tr>
          </thead>
          <tbody>
            {this.state.filteredData.map(contacto=>{
              return(
                <tr>
                  <td>{contacto.id}</td>
                  <td>{contacto.nombre}</td>
                  <td>{contacto.apellido}</td>
                  <td>{contacto.telefono}</td>
                  <td>{contacto.email}</td>
                  <td>{contacto.fecha_nac}</td>
                  <td>
                    <button className="btn btn-primary" onClick={()=>{this.seleccionarContacto(contacto); this.modalInsertar()}}><FontAwesomeIcon icon={faEdit}/></button>
                    {"   "}
                    <button className="btn btn-danger" onClick={()=>{this.seleccionarContacto(contacto); this.setState({modalEliminar: true})}}><FontAwesomeIcon icon={faTrashAlt}/></button>
                  </td>
                </tr>
              )
            })}
          </tbody>
        </table>
        {/* Modal para creacion de contactos  */}
        <Modal isOpen={this.state.modalInsertar}>
          <ModalHeader style={{display: 'block'}}>
            <span style={{float: 'right'}} onClick={()=>this.modalInsertar()}>x</span>
          </ModalHeader>
          <ModalBody>
            <div className="form-group">
              <label htmlFor="id">ID</label>
              <input className="form-control" type="number" name="id" id="id" readOnly onChange={this.handleChange} value={form?form.id: this.state.data.length+1}/>
              <br />
              <label htmlFor="nombre">Nombre</label>
              <input className="form-control" type="text" name="nombre" id="nombre" onChange={this.handleChange} value={form?form.nombre: ''}/>
              <br />
              <label htmlFor="apellido">Apellido</label>
              <input className="form-control" type="text" name="apellido" id="apellido" onChange={this.handleChange} value={form?form.apellido: ''}/>
              <br />
              <label htmlFor="telefono">Teléfono</label>
              <input className="form-control" type="text" name="telefono" id="telefono" onChange={this.handleChange} value={form?form.telefono: ''}/>
              <br />
              <label htmlFor="email">Email</label>
              <input className="form-control" type="text" name="email" id="email" onChange={this.handleChange} value={form?form.email: ''}/>
              <br />
              <label htmlFor="fecha_nac">Fecha nacimiento</label>
              <input className="form-control" type="date" name="fecha_nac" id="fecha_nac" onChange={this.handleChange} value={form?form.fecha_nac: ''}/>
              <label htmlFor="estado">Estado</label>
              <input className="form-control" type="boolean" name="estado" id="estado" onChange={this.handleChange} value={form?form.estado: ''}/>
              <br />
            </div>
          </ModalBody>
          
          {/* Modal para insertar o actualizar contacto en funcion del valor de tipoModal */}
          <ModalFooter>
            {this.state.tipoModal==='insertar'?
              <button className="btn btn-success" onClick={()=>this.peticionPost()}>
              Insertar
            </button>: <button className="btn btn-primary" onClick={()=>this.peticionPut()}>
              Actualizar
            </button>}
            <button className="btn btn-danger" onClick={()=>this.modalInsertar()}>Cancelar</button>
          </ModalFooter>
        </Modal>
        
        {/* Modal para eliminar contactos */}
        <Modal isOpen={this.state.modalEliminar}>
          <ModalBody>
              Estás seguro que deseas eliminar el contacto {form && form.nombre}?
          </ModalBody>
          <ModalFooter>
            <button className="btn btn-danger" onClick={()=>this.peticionDelete()}>Sí</button>
            <button className="btn btn-secundary" onClick={()=>this.setState({modalEliminar: false})}>No</button>
          </ModalFooter>
        </Modal>
        
        {/* Modal para elimianr contactos de forma logica */}
        <Modal isOpen={this.state.modalEliminarLogico}>
          <ModalBody>
              Estás seguro que deseas eliminar de forma lógica el contacto {form && form.nombre}?
          </ModalBody>
          <ModalFooter>
            <button className="btn btn-danger" onClick={()=>this.peticionDeleteLogico()}>Sí</button>
            <button className="btn btn-secundary" onClick={()=>this.setState({modalEliminarLogico: false})}>No</button>
          </ModalFooter>
        </Modal>
      </div>
    );
  }
}
export default App;
