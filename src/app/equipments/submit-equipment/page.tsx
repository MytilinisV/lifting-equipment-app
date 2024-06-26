"use client"

import {Button, Card, CardActions, CardContent, CardHeader, Grid, Snackbar, TextField} from "@mui/material";
import * as React from "react";
import {useState} from "react";
import AddIcon from "@mui/icons-material/Add";
import HomeIcon from "@mui/icons-material/Home";
import {useForm, FormProvider} from "react-hook-form";
import {useRouter} from "next/navigation";
import ControlledTextField from "@/components/ControlledTextField";

// Displays a page where you can submit a new equipment. Has various text boxes for equipment submission.

type EquipmentCreateDTO = {
  id: string;
  serialNumber: string;
  model: string;
  manufacturer: string;
  dateManufactured: number;
  tin: number;
}
export default function SubmitEquipmentPage() {
  const [isSnackbarOpen, setIsSnackbarOpen] = useState(false)
  const [snackbarMessage, setSnackbarMessage] = useState('')
  const form = useForm<EquipmentCreateDTO>({})
  form.watch()
  const router = useRouter()

  function handleOnSubmit() {
    fetch('http://localhost:8080/api/equipments', {
      method: 'POST', headers: {'Content-Type': 'application/json'}, body:
        JSON.stringify(form.getValues())
    })
      .then((res) => {
        if (res.status === 201) {
          router.push('/')
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

  return (
    <FormProvider {...form}>
      <Snackbar
        open={isSnackbarOpen}
        autoHideDuration={2500}
        onClose={() => setIsSnackbarOpen(false)}
        message={snackbarMessage}
      />
      <Grid mx={20}>
        <Card variant={'outlined'}>
          <CardHeader title={'Submit new Equipment'}/>
          <Card variant={'outlined'}>
            <CardContent>
              <Grid container spacing={2} md={4} mx={"auto"}>
                <Grid item sm={4}>
                  <ControlledTextField isRequired={false} id={'manufacturer'} label={'Manufacturer'}/>
                </Grid>
                <Grid item sm={4}>
                  <ControlledTextField isRequired={false} id={'model'} label={'Model'}/>
                </Grid>
                <Grid item sm={4}>
                  <ControlledTextField isRequired={true} id={'serialNumber'} label={'Serial Number'}/>
                </Grid>
                <Grid item sm={4} mx={"auto"}>
                  <ControlledTextField isRequired={false} id={'dateManufactured'} label={'Date Manufactured'}/>
                </Grid>
                <Grid item sm={4} mx={"auto"}>
                  <ControlledTextField isRequired={false} id={'tin'} label={'Tax Identification Number'}/>
                </Grid>
              </Grid>

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
                          startIcon={<AddIcon/>}>Submit</Button>
                </Grid>
              </Grid>
            </CardActions>
          </Card>
        </Card>
      </Grid>
    </FormProvider>
  )
}