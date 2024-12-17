import React, {useEffect, useState} from 'react';
import Button from "../components/Button";
import { useNavigate, useLocation, useParams } from 'react-router-dom';
import {TextField} from "@mui/material";

/*
 * The AdminEdit page handles the creation and editing of wine, events and distributor.
 */
const AdminEdit = () => {

    // The item is received through the useLocation state, if there is any. If not, create a default item
    const [item, setItem] = useState(useLocation().state?.item || { id: -1, name: '', description: '', price: '', imageURL: 'image', stock: '' });
    //Category name comes from the path parameters
    const { category } = useParams();
    const navigate = useNavigate();

    //This function converts items into JSON objects so it can be passed to the API handle
    const itemAsJSON = () => {
        if (category === "wine") {
            return {
                ID: item?.id,
                description: item.description,
                imageURL: item.imageURL,
                price: item.price,
                stock: item.stock,
                name: item.name
            }
        } else if (category === "event") {
            return {
                ID: item.id,
                date: item.date,
                description: item.description,
                imageURL: item.imageURL,
                location: item.location,
                time: item.time,
                title: item.title
            }
        } else if (category === "distributor") {
            return {
                ID: item.id,
                location: item.location,
                name: item.name,
                websiteURL: item.websiteURL
            }
        }
    }

    //The createAndEdit API for the given category is used to either create or edit an item
    const updateItem = () => {

        //Save the uploaded image to the item imageURL
        saveImage();

        //Create or edit item based on its JSON format
        fetch(`/api/${category}/admin/createAndEdit/${item.id}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(itemAsJSON()),
        })
            .then(response => response.json())
            .then((data) => {
                console.log("Updated item: ", data);
                navigate(`/administrator`);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    };

    //Delete an item using the delete API
    const deleteItem = () => {
        if (window.confirm(`Are you sure you want to delete ${item.name}`)) {
            fetch(`/api/${category}/admin/delete/${item.id}`, {
                method: 'POST',
            })
                .then((response) => response.json())
                .then(() => {
                    navigate('/administrator');
                })
                .catch((error) => console.error('Error deleting item:', error));
        }
    };

    //The 'image' state contains the local URL string if there is an imageURL in the item.
    const [image, setImage] = useState(item.imageURL!=='image' ? item.imageURL:'');
    //The 'file' state will contain the image file itself, if relevant.
    const [file, setFile] = useState();

    //When image is uploaded, save it to both states in the correct format
    const uploadImage = (e)=> {
        setImage(URL.createObjectURL(e.target.files[0]));
        setFile(e.target.files[0]);
    }

    //This function saves the image to the database
    const saveImage = () => {
        //Name of the file is based on the name/title of the item
        const name = (category==='wine' ? item.name :  item.title);

        //Attatch file and custom name to formData
        const formData = new FormData();
        formData.append('file', file);
        formData.append('customName', name)

        //The formData is now posted to the upload API and is added to the 'images' folder
        fetch('/api/images/admin/upload', {
            method: 'POST',
            body: formData
        })
            .then(response => response.text())
            .then(result => console.log('Success:', result))
            .catch(error => console.error('Error:', error));

        //update the imageURL for the item
        item.imageURL = `http://127.0.0.1:8080/api/images/${name}.png`
    }

    //This state holds the title of the page
    const [title, setTitle] = useState('');

    //This function sets the correct page title
    const setPageTitle = () => {
        let title = '';
        if(item.id === -1) {
            title += 'Opret ';
        } else {
            title += 'Redigér ';
        }
        if (category === 'wine') {
            title += 'vin';
        } else if (category === 'event'){
            title += 'event';
        } else {
            title += 'forhandler';
        }
        return title;
    }

    //On mount: Set the title using the setPageTitle function.
    useEffect(() => {
        setTitle(setPageTitle);
    }, []);

    //Update the item to match the administrator input in the field
    const setField = (e) => {
        const { name, value } = e.target;
        setItem((prevItem) => ({
            ...prevItem,
            [name]: value,
        }));
    }

    return (
        <div className="container justify-content-center">
            <div className="col-6 py-5">
                <h1 className="header-large">{title}</h1>
                {/* Only show delete button on existing item */}
                {item.id!==-1 &&
                    <div>
                        <Button text="Slet" onClick={deleteItem}/>
                    </div>
                }
                {/* Ability to upload images to wine or event items */}
                {(category === "wine" || category === "event") &&
                    <div className="col">
                        <img
                            src={image}
                            alt='image'
                            className="card-img-top"
                            style={{objectFit: 'contain', maxHeight: '200px', maxWidth: '200px'}}
                        />
                        <input type="file" onChange={uploadImage}/>
                    </div>
                }
                {/* Display text fields for wine */}
                {category === "wine" &&
                    <EditWine item={item} setField={setField}/>
                }
                {/* Display text fields for event */}
                {category === "event" &&
                    <EditEvent item={item} setField={setField} />
                }
                {/* Display text fields for distributor */}
                {category==="distributor" &&
                    <EditDistributor item={item} setField={setField} />
                }

                <div>
                    <div className="d-flex justify-content-around">
                        <Button text="Gem" onClick={updateItem} />
                        <Button text="Annullér" onClick={() => navigate('/administrator')}  />
                    </div>
                </div>
            </div>
        </div>
    );
}

//The editField component is used to reduce redundancy for text fields that are generally formatted similarly
const EditField = ({title, field, item, setField, placeholder}) => {
    return (
        <TextField
            label={title}
            name={field}
            variant="outlined"
            multiline
            fullWidth
            value={item[field]}
            onChange={setField}
            margin="normal"
            defaultValue={placeholder}
        >
        </TextField>
    )
}

//Text fields for wine
const EditWine = ( { item, setField } ) => {
    return (
        <div>

            < EditField
                title="Navn"
                field="name"
                item={item}
                setField={setField}
                placeholder="f.eks. Havtorben"
            />
            < EditField
                title="Beskrivelse"
                field="description"
                item={item}
                setField={setField}
            />
            < EditField
                title="Antal"
                field="stock"
                item={item}
                setField={setField}
                placeholder="f.eks 100"
            />
            < EditField
                title="Pris"
                field="price"
                item={item}
                setField={setField}
                placeholder="f.eks 189"
            />

        </div>
    )
}

//Text fields for event
const EditEvent = ({item, setField}) => {
    return (
        <div>
            <EditField
                title="Navn"
                field="title"
                item={item}
                setField={setField}
            />
            <EditField
                title="Dato"
                field="date"
                item={item}
                setField={setField}
                placeholder="DD/MM/YYYY"
            />
            <EditField
                title="Tidspunkt"
                field="time"
                item={item}
                setField={setField}
                placeholder="HH:MM-HH:MM"
            />
            <EditField
                title="Sted"
                field="location"
                item={item}
                setField={setField}
            />
            <EditField
                title="Beskrivelse"
                field="description"
                item={item}
                setField={setField}
            />
        </div>
    );
}

//Text fields for distributor
const EditDistributor = ( { item, setField } ) => {
    return (
        <div>
            <EditField
                title="Navn"
                field="name"
                item={item}
                setField={setField}
            />
            <EditField
                title="Adresse"
                field="location"
                item={item}
                setField={setField}
            />
            <EditField
                title="Hjemmeside"
                field="websiteURL"
                item={item}
                setField={setField}
                placeholder="https://www.br.dk/"
            />
        </div>
    );
}

export default AdminEdit;