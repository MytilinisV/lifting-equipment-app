"use client"
import {GridColDef} from "@mui/x-data-grid";
import {
  Button,
  Card,
  CardActions,
  CardContent,
  CardHeader, CircularProgress,
  Dialog,
  Grid,
  Snackbar,
  TextField,
  Typography
} from "@mui/material";
import * as React from "react";
import AddIcon from "@mui/icons-material/Add";
import {useEffect, useState} from "react";
import {useRouter, useSearchParams} from "next/navigation";
import {CustomerResponseDTO} from "@/app/page";
import {EquipmentResponseDTO} from "@/app/page"
import {FormProvider, useForm} from "react-hook-form";
import ControlledTextField from "@/components/ControlledTextField";
import HomeIcon from "@mui/icons-material/Home";
import UpdateIcon from "@mui/icons-material/Update";
import DeleteIcon from "@mui/icons-material/Delete";
import {auto} from "@popperjs/core";

// Displays a page where you can submit a new equipment. Has various text boxes for equipment submission.

type EquipmentEditDTO = {
  id: string;
  serialNumber: string;
  model: string;
  manufacturer: string;
  dateManufactured: number;
  customerName: string;
  dateAdded: string;
}
export default function EditEquipmentPage() {

  const [isSnackbarOpen, setIsSnackbarOpen] = useState(false)
  const [snackbarMessage, setSnackbarMessage] = useState('')
  const [isDialogOpen, setIsDialogOpen] = useState(false)
  const router = useRouter();
  const searchParams = useSearchParams();
  const serialNumber = searchParams.get('serialNumber');
  const [responseData, setResponseData] = useState<EquipmentResponseDTO>({} as EquipmentResponseDTO)
  const form = useForm<EquipmentEditDTO>({})

  form.watch()
  const [isLoading, setIsLoading] = useState(false)

  async function findEquipmentBySerialNumber() {
    setIsLoading(true)
    const equipments = await fetch(`http://localhost:8080/api/equipments/serialNumber/${serialNumber}`, {method: 'GET'})
      .then(res => {
        if (res.status === 200) {
          return res.json();
        }
      }) as EquipmentResponseDTO[]

    const equipment = equipments.pop() || {} as EquipmentResponseDTO
    form.setValue('id', equipment.id)
    form.setValue('serialNumber', equipment.serialNumber)
    form.setValue('model', equipment.model)
    form.setValue('manufacturer', equipment.manufacturer)
    form.setValue('dateManufactured', equipment.dateManufactured)

    setResponseData(equipment);
    setIsLoading(false);
  }

  function handleOnSubmit() {
    fetch(`http://localhost:8080/api/equipments/${serialNumber}`, {
      method: 'PUT', headers: {'Content-Type': 'application/json'}, body:
        JSON.stringify(form.getValues())
    })
      .then((res) => {
        if (res.status === 200) {
          router.push('/equipments')
        } else {
          res.text().then(res => {
            setSnackbarMessage(JSON.stringify(res))
            setIsSnackbarOpen(true)
          }).catch(reason => {
            setSnackbarMessage("Oops, an unexpected error has occurred")
            setIsSnackbarOpen(true)
          })
        }
      })
  }

  function handleDelete() {
    fetch(`http://localhost:8080/api/equipments/${serialNumber}`, {
      method: 'DELETE', headers: {'Content-Type': 'application/json'}, body:
        JSON.stringify(form.getValues())
    })
      .then((res) => {
        if (res.status === 200) {
          router.push('/equipments')
        } else {
          res.text().then(res => {
            setSnackbarMessage(JSON.stringify(res))
            setIsSnackbarOpen(true)
          }).catch(reason => {
            setSnackbarMessage("Oops, an unexpected error has occurred")
            setIsSnackbarOpen(true)
          })
        }
      })
  }

  function onError() {
    console.log(form)
  }

  useEffect(() => {
    findEquipmentBySerialNumber().then()
  }, []);

  return (
    <FormProvider {...form} >
      <Snackbar
        open={isSnackbarOpen}
        autoHideDuration={2500}
        onClose={() => setIsSnackbarOpen(false)}
        message={snackbarMessage}
      />
      <Grid mx={20}>
        <Card variant={'outlined'}>
          <CardHeader title={'Edit Equipment'}/>
          <Card variant={'outlined'}>
            <CardContent>
              {isLoading ? (<Grid container justifyContent={"center"}>
                  <CircularProgress color="inherit"/>
                </Grid>) : (
              <Grid container spacing={2} md={4} mx={"auto"}>
                <Grid item sm={4}>
                  <ControlledTextField isRequired={false} id={'manufacturer'} label={'manufacturer'}
                                       defaultValue={responseData.manufacturer}/>
                </Grid>
                <Grid item sm={4}>
                  <ControlledTextField isRequired={false} id={'model'} label={'model'}
                                       defaultValue={responseData.model}/>
                </Grid>
                <Grid item sm={4}>
                  <ControlledTextField isRequired={true} id={'serialNumber'} label={'serialNumber'}
                                       defaultValue={responseData.serialNumber}/>
                </Grid>
                <Grid item sm={4}>
                  <ControlledTextField isRequired={false} id={'dateManufactured'} label={'dateManufactured'}
                                       defaultValue={responseData.dateManufactured}/>
                </Grid>
              </Grid>
                )}
            </CardContent>
            <CardActions>
              <Grid container spacing={2} justifyContent={"center"}>
                <Grid item>
                  <Button type={'button'} variant="contained" color={'primary'}
                          onClick={() => router.push('/')}
                          startIcon={<HomeIcon/>}>Home</Button>
                </Grid>
                <Grid item>
                  <Button type={'submit'} variant="contained" color={'success'}
                          onClick={form.handleSubmit(handleOnSubmit, onError)}
                          startIcon={<UpdateIcon/>}>Update</Button>
                </Grid>
                <Grid item>
                  <Button variant="outlined" color={'error'} onClick={() => setIsDialogOpen(true)}
                          startIcon={<DeleteIcon/>}>Delete</Button>
                </Grid>
              </Grid>
            </CardActions>
          </Card>
        </Card>
      </Grid>
      <Dialog open={isDialogOpen} onClose={() => setIsDialogOpen(false)} fullWidth>
        <Card>
          <CardHeader title={'You are about to delete an Equipment.'}/>
          <CardContent>
            <Typography variant={'h6'}>Are you sure?</Typography>
          </CardContent>
          <CardActions>
            <Grid container spacing={2} justifyContent={"end"}>
              <Grid item>
                <Button type={'button'} variant="contained" onClick={handleDelete} color={'success'}>Yes</Button>
              </Grid>
              <Grid item>
                <Button type={'button'} variant="contained" onClick={() => setIsDialogOpen(false)}
                        color={'error'}>No</Button>
              </Grid>
            </Grid>
          </CardActions>
        </Card>
      </Dialog>
    </FormProvider>)
}