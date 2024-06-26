"use client"

import {
  Button,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  CircularProgress,
  Dialog,
  Grid,
  Snackbar,
  Typography
} from "@mui/material";
import * as React from "react";
import {useEffect, useState} from "react";
import UpdateIcon from "@mui/icons-material/Update";
import {useRouter, useSearchParams} from "next/navigation";
import {CustomerResponseDTO} from "@/app/page";
import ControlledTextField from "@/components/ControlledTextField";
import {FormProvider, useForm} from "react-hook-form";
import HomeIcon from "@mui/icons-material/Home";
import DeleteIcon from "@mui/icons-material/Delete";

type CustomerEditDTO = {
  id: string;
  customerName: string;
  tin: number;
  postCode: number;
  phoneNumber: number;
  address: string;
  email: string;
}

// Displays a page where you can submit a new customer. Has various text boxes for customer submission.
export default function EditCustomerPage() {
  const [isSnackbarOpen, setIsSnackbarOpen] = useState(false)
  const [snackbarMessage, setSnackbarMessage] = useState('')
  const [isDialogOpen, setIsDialogOpen] = useState(false)
  const router = useRouter();
  const searchParams = useSearchParams();
  const tin = searchParams.get('tin');
  const [responseData, setResponseData] = useState<CustomerResponseDTO>({} as CustomerResponseDTO)
  const form = useForm<CustomerEditDTO>({})

  form.watch()
  const [isLoading, setIsLoading] = useState(false)

  async function findCustomerByTin() {
    setIsLoading(true)
    const customers = await fetch(`http://localhost:8080/api/customers/tin/${tin}`, {method: 'GET'})
      .then(res => {
        if (res.status === 200) {
          return res.json();
        }
      }) as CustomerResponseDTO[]

    const customer = customers.pop() || {} as CustomerResponseDTO
    form.setValue('id', customer.id)
    form.setValue('customerName', customer.customerName)
    form.setValue('tin', customer.tin)
    form.setValue('address', customer.address)
    form.setValue('phoneNumber', customer.phoneNumber)
    form.setValue('postCode', customer.postCode)
    form.setValue('email', customer.email)

    setResponseData(customer);
    setIsLoading(false);
  }

  function handleOnSubmit() {
    fetch(`http://localhost:8080/api/customers/${tin}`, {
      method: 'PUT', headers: {'Content-Type': 'application/json'}, body:
        JSON.stringify(form.getValues())
    })
      .then((res) => {
        if (res.status === 200) {
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

  function handleDelete() {
    fetch(`http://localhost:8080/api/customers/${tin}`, {
      method: 'DELETE', headers: {'Content-Type': 'application/json'}, body:
        JSON.stringify(form.getValues())
    })
      .then((res) => {
        if (res.status === 200) {
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

  useEffect(() => {
    findCustomerByTin().then()
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
          <CardHeader title={'Edit Customer'}/>
          <Card variant={'outlined'}>
            <CardContent>
              {isLoading ? (<Grid container justifyContent={"center"}>
                <CircularProgress color="inherit"/>
              </Grid>) : (
                <Grid container spacing={2} md={4} mx={"auto"}>
                  <Grid item sm={4}>
                    <ControlledTextField isRequired={true} id={'customerName'} label={'Customer Name'}
                                         defaultValue={responseData.customerName}/>
                  </Grid>
                  <Grid item sm={4}>
                    <ControlledTextField isRequired={true} id={'tin'} type={'number'}
                                         label={'Tax Identification Number'} defaultValue={responseData.tin}/>
                  </Grid>
                  <Grid item sm={4}>
                    <ControlledTextField isRequired={false} id={'address'} label={'Address'}
                                         defaultValue={responseData.address}/>
                  </Grid>
                  <Grid item sm={4}>
                    <ControlledTextField isRequired={false} id={'phoneNumber'} type={'number'} label={'Phone Number'}
                                         defaultValue={responseData.phoneNumber}/>

                  </Grid>
                  <Grid item sm={4}>
                    <ControlledTextField isRequired={false} id={'postCode'} type={'number'} label={'Postal Code'}
                                         defaultValue={responseData.postCode}/>
                  </Grid>
                  <Grid item sm={4}>
                    <ControlledTextField isRequired={false} id={'email'} label={'Email'}
                                         defaultValue={responseData.email}/>
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
          <CardHeader title={'You are about to delete a Customer.'}/>
          <CardContent>
            <Typography variant={'h6'}>Are you sure?</Typography>
          </CardContent>
          <CardActions>
            <Grid container spacing={2} justifyContent={"end"}>
              <Grid item>
                <Button type={'button'} variant="contained" onClick={handleDelete} color={'success'}>Yes</Button>
              </Grid>
              <Grid item>
                <Button type={'button'} variant="contained" onClick={() => setIsDialogOpen(false)} color={'error'}>No</Button>
              </Grid>
            </Grid>
          </CardActions>
        </Card>
      </Dialog>
    </FormProvider>
  )
}