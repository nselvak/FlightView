export interface Login {
    // id: string
    email: string 
    password: string
    key: number
}

export interface LoginSummary {
    // id: string
    email: string 
    password: string
}

export interface  Response{
    code: number
    message?: string
    data?: any
    
}

export interface Flight {
    // id: string
    email: string 
    adult: number
    origin: string
    destination: string
    date: string
}


export interface DataBase {
    // id: string
    id?: number;
    email: string 
    data: any
}
