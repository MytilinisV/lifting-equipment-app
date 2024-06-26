"use client"

import {Button, Card, CardActions, CardContent, CardHeader, Grid, Snackbar} from "@mui/material";
import * as React from "react";
import {useState} from "react";
import LoginIcon from "@mui/icons-material/Login"
import {FormProvider, useForm} from "react-hook-form";
import {useRouter} from "next/navigation";
import RegisterIcon from "@mui/icons-material/AppRegistration";
import ControlledTextField from "@/components/ControlledTextField";

type UserLoginDTO = {
  username: string,
  password: string
}

export default function LoginPage() {
  const [snackbarMessage, setSnackbarMessage] = useState('')
  const [isSnackbarOpen, setIsSnackbarOpen] = useState(false)
  const form = useForm<UserLoginDTO>({})
  form.watch()
  const router = useRouter()

  function handleOnLogin() {
    fetch('http://localhost:8080/users/login', {
      method: 'POST', headers: {'Content-Type': 'application/json'}, body:
        JSON.stringify(form.getValues())
    })
      .then((res) => {
        if (res.status === 201) {
          router.push('/login')
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
          <CardHeader title={'Login'}/>
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
                          onClick={form.handleSubmit(handleOnLogin, onError)}
                          startIcon={<LoginIcon/>}>Login</Button>
                </Grid>
                <Grid item>
                  <Button type={'button'} variant="contained" color={'primary'}
                          onClick={() => router.push('/register')}
                          startIcon={<RegisterIcon/>}>Register
                  </Button>
                </Grid>
              </Grid>
            </CardActions>
          </Card>
        </Card>
      </Grid>
    </FormProvider>
  )
}