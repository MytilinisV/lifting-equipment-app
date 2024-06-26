"use client"

import {Button, Card, CardActions, CardContent, CardHeader, Grid, Snackbar} from "@mui/material";
import * as React from "react";
import {useState} from "react";
import AddIcon from "@mui/icons-material/Add";
import HomeIcon from "@mui/icons-material/Home";
import {useRouter} from "next/navigation";
import {FormProvider, useForm} from "react-hook-form"
import ControlledTextField from "@/components/ControlledTextField";

type CustomerCreateDTO = {
  customerName: string;
  tin: number;
  postCode: number;
  phoneNumber: number;
  address: string;
  email: string;
}

// Displays a page where you can submit a new customer. Has various text boxes for customer submission.
export default function SubmitCustomerPage() {
  const [isSnackbarOpen, setIsSnackbarOpen] = useState(false)
  const [snackbarMessage, setSnackbarMessage] = useState('')
  const form = useForm<CustomerCreateDTO>({})
  form.watch()
  const router = useRouter()

  function handleOnSubmit() {
    fetch('http://localhost:8080/api/customers', {
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
          <CardHeader title={'Submit new Customer'}/>
          <Card variant={'outlined'}>
            <CardContent>
              <Grid container spacing={2} md={4} mx={"auto"}>
                <Grid item sm={4}>
                  <ControlledTextField isRequired={true} id={'customerName'} label={'Customer Name'}/>
                </Grid>
                <Grid item sm={4}>
                  <ControlledTextField isRequired={true} id={'tin'} type={'number'}
                                       label={'Tax Identification Number'}/>
                </Grid>
                <Grid item sm={4}>
                  <ControlledTextField isRequired={false} id={'address'} label={'Address'}/>
                </Grid>
                <Grid item sm={4}>
                  <ControlledTextField isRequired={false} id={'phoneNumber'} type={'number'} label={'Phone Number'}/>

                </Grid>
                <Grid item sm={4}>
                  <ControlledTextField isRequired={false} id={'postCode'} type={'number'} label={'Postal Code'}/>
                </Grid>
                <Grid item sm={4}>
                  <ControlledTextField isRequired={false} id={'email'} label={'Email'}/>
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