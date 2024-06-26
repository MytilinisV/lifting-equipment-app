"use client"

import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import SearchIcon from '@mui/icons-material/Search';
import {useRouter} from "next/navigation";
import LoginIcon from "@mui/icons-material/Login";
import RegisterIcon from "@mui/icons-material/AppRegistration";


export default function NavBar() {
  const router = useRouter()

  return (
    <Box sx={{flexGrow: 1}} mb={3}>
      <AppBar color={'primary'} position="static">
        <Toolbar>
          <Button color="inherit" onClick={() => router.push('/')} startIcon={<SearchIcon/>}>Customers</Button>
          <Button color="inherit" onClick={() => router.push('/equipments')}
                  startIcon={<SearchIcon/>}>Equipments</Button>
          <Button color="inherit" onClick={() => router.push('/login')} startIcon={<LoginIcon/>}>Login</Button>
          <Button color="inherit" onClick={() => router.push('/register')} startIcon={<RegisterIcon/>}>Register</Button>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
