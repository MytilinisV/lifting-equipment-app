"use client"
import * as React from 'react';
import {useEffect, useState} from 'react';
import {DataGrid, GridColDef} from '@mui/x-data-grid';
import DeleteIcon from '@mui/icons-material/Delete';
import SearchIcon from '@mui/icons-material/Search';
import AddIcon from '@mui/icons-material/Add'
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
import {useRouter} from "next/navigation";

// The main application page. It displays all customers of the database.

export type CustomerResponseDTO = {
  id: string,
  customerName: string,
  tin: number,
  postCode: number,
  phoneNumber: number,
  address: string,
  email: string,
  equipments: EquipmentResponseDTO[];
}

export type EquipmentResponseDTO = {
  id: string,
  serialNumber: string,
  model: string,
  manufacturer: string,
  dateManufactured: number
  dateAdded: string
}

const columns: GridColDef[] = [
  {field: 'customerName', headerName: 'Customer Name', width: 300},
  {field: 'tin', headerName: 'Tax Identification Number', width: 180},
  {field: 'address', headerName: 'Address', width: 300},
  {field: 'postCode', headerName: 'Postal Code', width: 130},
  {field: 'phoneNumber', headerName: 'Phone Number', width: 130},
  {field: 'email', headerName: 'Email', width: 400},
];

export default function Home() {
  const [searchType, setSearchType] = useState('customerName')
  const [identifier, setIdentifier] = useState('')
  const [customers, setCustomers] = useState<CustomerResponseDTO[]>([])
  const [isLoading, setIsLoading] = useState(false)
  const [isSnackbarOpen, setIsSnackbarOpen] = useState(false)
  const router = useRouter()

  async function handleSearch() {
    if (identifier === '') {
      getAllCustomers().then()
      return;
    }

    setIsLoading(true)
    const customers = await fetch(`http://localhost:8080/api/customers/${searchType}/${identifier}`, {
      method: "GET",
    }).then(res => {
      if (res.status === 200) {
        return res.json()
      }

      setIsSnackbarOpen(true)
      return []
    }, reason => {
      setIsSnackbarOpen(true)
    }).finally(() => setIsLoading(false)) as CustomerResponseDTO[] || []

    setCustomers(customers || []);
  }

  function handleSelect(e: SelectChangeEvent<unknown>) {
    setSearchType(e.target.value as string);
  }

  function handleInput(e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) {
    setIdentifier(e.target.value as string);
  }

  function getInputLabel() {
    switch (searchType) {
      case 'customerName':
        return 'Customer Name'
      case 'tin':
        return 'Tax Identification Number'
      case 'postCode':
        return 'Postal Code'
      default:
        return 'Customer Name'
    }
  }

  async function getAllCustomers() {
    setIsLoading(true)
    const customers = await fetch('http://localhost:8080/api/customers', {
      method: "GET",
    }).then(res => {
      if (res.status === 200) {
        return res.json()
      }

      setIsSnackbarOpen(true)
      return []
    }, reason => {
      setIsSnackbarOpen(true)
    }).finally(() => setIsLoading(false)) as CustomerResponseDTO[] || []

    setCustomers(customers || []);
  }

  useEffect(() => {
    getAllCustomers().then();
  }, []);

  return (
    <>
      <Snackbar
        open={isSnackbarOpen}
        autoHideDuration={2500}
        onClose={() => setIsSnackbarOpen(false)}
        message="No customers were found"
      />
      <Card variant={'outlined'}>
        <CardHeader title={'Customer Search'}/>
        <CardContent>
          <Grid container spacing={3} justifyContent={"center"}>
            <Grid item xs={3} md={2}>
              <FormControl fullWidth>
                <InputLabel id={"demo-simple-select-label"}>Search</InputLabel>
                <Select
                  onChange={handleSelect}
                  labelId={"demo-simple-select-label"}
                  id={"demo-simple-select"}
                  label={"Search"}
                  defaultValue={'customerName'}
                >
                  <MenuItem value={"customerName"}>Customer Name</MenuItem>
                  <MenuItem value={"tin"}>Tax Identification Number</MenuItem>
                  <MenuItem value={"postCode"}>Postal Code</MenuItem>
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
              <Button variant="contained" color={'success'} onClick={() => router.push('/submit-customer')}
                      startIcon={<AddIcon/>}>Add</Button>
            </Grid>
          </Grid>
        </CardContent>
      </Card>
      <Card variant={'outlined'}>
        <CardHeader title={'Customers'} subheader={'Double click on a table row to edit Customer\'s details'}/>
        <CardContent>
          <DataGrid
            rows={customers}
            columns={columns}
            initialState={{
              pagination: {
                paginationModel: {page: 0, pageSize: 5},
              },
            }}
            pageSizeOptions={[5, 10]}
            // checkboxSelection
            loading={isLoading}
            autoHeight={true}
            onRowDoubleClick={(params, event, details) => router.push(`/edit-customer?tin=${params.row.tin}`)}
          />
        </CardContent>
      </Card>
    </>
  );
}
