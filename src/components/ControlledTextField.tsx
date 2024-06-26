import {Controller, RegisterOptions, useFormContext} from "react-hook-form";
import {TextField} from "@mui/material";
import * as React from "react";

export type TextFieldProps = {
  isRequired: boolean
  id: string
  label: string
  defaultValue?: string | number
  type?: string
  helperText?: string
  rules?: RegisterOptions;
}

export default function ControlledTextField(props: TextFieldProps) {
  const {isRequired, label, id, defaultValue, type, helperText, rules} = props;
  const form = useFormContext();

  form.trigger(id)
  return (
    <Controller
      render={({field, fieldState}) => (
        <TextField
          type={type ? type : 'text'}
          required={isRequired}
          id={id}
          error={fieldState.invalid}
          helperText={fieldState.invalid && (isRequired ? 'Field Required' : helperText)}
          label={label}
          defaultValue={defaultValue ? defaultValue : ''}
          onChange={(e) => form.setValue(id, e.target.value || '')}
        />)}
      control={form.control}
      name={id}
      rules={{...rules, required: isRequired}}
    />
  )
}