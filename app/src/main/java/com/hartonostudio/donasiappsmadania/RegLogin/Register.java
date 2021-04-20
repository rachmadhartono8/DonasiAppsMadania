package com.hartonostudio.donasiappsmadania.RegLogin;

public class Register {

    private String nama, email, password;
    public Register(String nama, String email, String password) {

            this.nama = nama;
            this.email = email;
            this.password = password;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
