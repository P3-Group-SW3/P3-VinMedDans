import React, {useEffect, useState} from 'react';
import Button from "../components/Button";
import { useNavigate, useLocation, useParams } from 'react-router-dom';
import {InputAdornment, TextField} from "@mui/material";

function AdminEdit() {
    const navigate = useNavigate();

    const [item, setItem] = useState(useLocation().state?.item || { id: -1, name: '', description: '', price: '', imageURL: 'image', stock: '' });
    const { category } = useParams();

    const [title, setTitle] = useState('');
    const [image, setImage] = useState(item.imageURL!=='image' ? item.imageURL:'');
    const [file, setFile] = useState();

    function uploadImage(e) {
        setImage(URL.createObjectURL(e.target.files[0]));
        setFile(e.target.files[0]);
    }

    const saveImage = () => {
        const name = (category==='wine' ? item.name :  item.title);
        console.log("Selected file:", file);
        console.log("Name: ", name);

        const formData = new FormData();
        formData.append('file', file);
        formData.append('customName', name)

        fetch('/api/images/admin/upload', {
            method: 'POST',
            body: formData
        })
            .then(response => response.text())
            .then(result => console.log('Success:', result))
            .catch(error => console.error('Error:', error));

        item.imageURL = `http://127.0.0.1:8080/api/images/${name}.png`
    }

    useEffect(() => {
        setTitle(setPageTitle);
        console.log("Item: ", item);
    }, []);

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

    const setField = (e) => {
        const { name, value } = e.target;
        setItem((prevItem) => ({
            ...prevItem,
            [name]: value,
        }));
        console.log(item);
    }

    const updateItem = () => {

        saveImage();

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

    return (
        <div className="container justify-content-center">
            <div className="col-6 py-5">
                <h1 className="header-large">{title}</h1>
                {item.id!==-1 &&
                    <div>
                        <Button text="Slet" onClick={deleteItem}/>
                    </div>
                }
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
                {category === "wine" &&
                    <EditWine item={item} setField={setField}/>
                }
                {category === "event" &&
                    <EditEvent item={item} setField={setField} />
                }
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
                endAdornment={<InputAdornment position="end">DKK</InputAdornment>}
            />

        </div>
    )
}

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