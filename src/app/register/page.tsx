"use client"

import {Button, Card, CardActions, CardContent, CardHeader, Grid, Snackbar} from "@mui/material";
import * as React from "react";
import {useState} from "react";
import RegisterIcon from "@mui/icons-material/AppRegistration"
import {FormProvider, useForm} from "react-hook-form";
import {useRouter} from "next/navigation";
import ControlledTextField from "@/components/ControlledTextField";
import LoginIcon from "@mui/icons-material/Login";

type UserRegisterDTO = {
  username: string,
  password: string,
}

export default function RegisterPage() {
  const [snackbarMessage, setSnackbarMessage] = useState('')
  const [isSnackbarOpen, setIsSnackbarOpen] = useState(false)
  const form = useForm<UserRegisterDTO>({})
  form.watch()
  const router = useRouter()

  function handleOnRegister() {
    fetch('http://localhost:8080/users/register', {
      method: 'POST', headers: {'Content-Type': 'application/json'}, body:
        JSON.stringify(form.getValues())
    })
      .then((res) => {
        if (res.status === 201) {
          router.push('/users/register')
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
      <Grid container justifyContent={"center"}>
        <Card variant={'outlined'}>
          <CardHeader title={'Register'}/>
          <Card variant={'outlined'}>
            <CardContent>
              <Grid container spacing={3} md={6} justifyContent={"center"} mx={"auto"}>
                <Grid item>
                  <ControlledTextField id={'username'} label={'Username'} isRequired/>
                </Grid>
                <Grid item>
                  <ControlledTextField id={'password'} type={"password"} label={'Password'} isRequired/>
                </Grid>
              </Grid>
            </CardContent>
            <CardActions>
              <Grid container spacing={3} md={12} justifyContent={"center"} mx={"auto"}>
                <Grid item>
                  <Button type={'button'} variant="contained" color={'primary'}
                          onClick={form.handleSubmit(handleOnRegister, onError)}
                          startIcon={<RegisterIcon/>}>Register
                  </Button>
                </Grid>
                <Grid item>
                  <Button type={'button'} variant="contained" color={'primary'}
                          onClick={() => router.push('/login')}
                          startIcon={<LoginIcon/>}>Login</Button>
                </Grid>
              </Grid>
            </CardActions>
          </Card>
        </Card>
      </Grid>
    </FormProvider>
  )
}