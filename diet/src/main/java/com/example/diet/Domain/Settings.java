package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Settings {
    private Long usr_id;
    private Float number;
    private Float shicaiqihe;
    private Float yingyangjiegou;
    private Float teshuxuqiu;
    private Float jijieshiling;
    private Float yinshipianhao;
}
