"use client"
import {DataGrid, GridColDef} from "@mui/x-data-grid";
import {
  Button,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  SelectChangeEvent,
  Snackbar,
  TextField
} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import AddIcon from "@mui/icons-material/Add";
import DeleteIcon from "@mui/icons-material/Delete";
import * as React from "react";
import {useEffect, useState} from "react";
import EditIcon from "@mui/icons-material/Edit";
import {useRouter} from "next/navigation";
import {router} from "next/client";
// import {useState} from "react";

// A page that displays the equipments submitted on a customer.

type EquipmentResponseDTO = {
  id: string,
  serialNumber: string,
  model: string,
  manufacturer: string,
  dateManufactured: number,
  customer: CustomerResponseDTO,
  dateAdded: string
}

type EquipmentRowDTO = {
  id: string,
  serialNumber: string,
  model: string,
  manufacturer: string,
  dateManufactured: number,
  customerName: string,
  dateAdded: string
}

type CustomerResponseDTO = {
  id: string,
  customerName: string,
  tin: number,
  postCode: number,
  phoneNumber: number,
  address: string,
  email: string,
}

const columns: GridColDef[] = [
  {field: 'manufacturer', headerName: 'Manufacturer', width: 130},
  {field: 'model', headerName: 'Model', width: 130},
  {field: 'serialNumber', headerName: 'Serial Number', width: 180},
  {field: 'dateManufactured', headerName: 'Date Manufactured', width: 150},
  {field: 'customerName', headerName: 'Customer', width: 200},
  {field: 'dateAdded', headerName: 'Date Added', width: 300},
]

function mapEquipmentToRowData(response: EquipmentResponseDTO[]): EquipmentRowDTO[] {
  return response.map(equipment => {
    return {
      id: equipment.id,
      serialNumber: equipment.serialNumber,
      model: equipment.model,
      manufacturer: equipment.manufacturer,
      dateManufactured: equipment.dateManufactured,
      customerName: equipment?.customer?.customerName || '-',
      dateAdded: equipment.dateAdded
    }
  })
}

export default function EquipmentsPage() {
  const [searchType, setSearchType] = useState('manufacturer')
  const [identifier, setIdentifier] = useState('')
  const [equipments, setEquipments] = useState<EquipmentRowDTO[]>([])
  const [isLoading, setIsLoading] = useState(false)
  const [isSnackbarOpen, setIsSnackbarOpen] = useState(false)
  const router = useRouter();

  async function getAllEquipments() {
    setIsLoading(true)
    const equipments = await fetch('http://localhost:8080/api/equipments', {
      method: 'GET',
    }).then(res => {
      if (res.status === 200) {
        return res.json()
      }

      setIsSnackbarOpen(true)
      return []
    }, reason => {
      setIsSnackbarOpen(true)
    }).finally(() => setIsLoading(false)) as EquipmentResponseDTO[] || []
    setEquipments(mapEquipmentToRowData(equipments) || []);
  }

  useEffect(() => {
    getAllEquipments().then();
  }, []);

  async function handleSearch() {
    if (identifier === '') {
      getAllEquipments().then()
      return;
    }

    setIsLoading(true)
    const equipments = await fetch(`http://localhost:8080/api/equipments/${searchType}/${identifier}`, {
      method: "GET",
    }).then(res => {
      if (res.status === 200) {
        return res.json()
      }

      setIsSnackbarOpen(true)
      return []
    }, reason => {
      setIsSnackbarOpen(true)
    }).finally(() => setIsLoading(false)) as EquipmentResponseDTO[] || []

    setEquipments(mapEquipmentToRowData(equipments) || []);
  }

  function handleSelect(e: SelectChangeEvent<unknown>) {
    setSearchType(e.target.value as string);
  }

  function handleInput(e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) {
    setIdentifier(e.target.value as string);
  }

  function getInputLabel() {
    switch (searchType) {
      case 'serialNumber':
        return 'Serial Number'
      case 'manufacturer':
        return 'Manufacturer'
    }
  }

  return (
    <>
      <Snackbar
        open={isSnackbarOpen}
        autoHideDuration={2500}
        onClose={() => setIsSnackbarOpen(false)}
        message="No equipments were found"
      />
      <Card variant={'outlined'}>
        <CardHeader title={'Equipment Search'}/>
        <CardContent>
          <Grid container spacing={3} justifyContent={"center"}>
            <Grid item xs={3} md={2}>
              <FormControl fullWidth>
                <InputLabel id={"demo-simple-select-label"}>Search</InputLabel>
                <Select
                  labelId={"demo-simple-select-label"}
                  id={"demo-simple-select"}
                  label={"Search"}
                  defaultValue={'manufacturer'}
                  onChange={handleSelect}
                >
                  <MenuItem value={"manufacturer"}>Manufacturer</MenuItem>
                  <MenuItem value={"serialNumber"}>Serial Number</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item xs={3} md={2}>
              <TextField fullWidth label={getInputLabel()} color={'primary'} onChange={handleInput}/>
            </Grid>
          </Grid>
        </CardContent>
        <CardContent>

          <Grid container spacing={2} justifyContent={'end'}>
            <Grid item xs={0}>
              <Button variant="contained" onClick={handleSearch} startIcon={<SearchIcon/>}>Search</Button>
            </Grid>
            <Grid item xs={0}>
              <Button variant="contained" color={'success'} onClick={() => router.push('equipments/submit-equipment')}
                      startIcon={<AddIcon/>}>Add</Button>
            </Grid>
          </Grid>
        </CardContent>
      </Card>
      <Card variant={'outlined'}>
        <CardHeader title={'Equipments'} subheader={'Double click on a table row to edit Equipment\'s details'}/>
        <CardContent>
          <DataGrid
            rows={equipments}
            columns={columns}
            initialState={{
              pagination: {
                paginationModel: {page: 0, pageSize: 5},
              },
            }}
            pageSizeOptions={[5, 10]}
            autoHeight={true}
            loading={isLoading}
            // checkboxSelection
            onRowDoubleClick={(params, event, details) => router.push(`/equipments/edit-equipment?serialNumber=${params.row.serialNumber}`)}
          />
        </CardContent>

      </Card>
    </>

  );
}