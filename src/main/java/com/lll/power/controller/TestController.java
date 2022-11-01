package com.lll.power.controller;

import com.lll.power.common.Result;
import com.lll.power.model.domain.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
public class TestController {

    @GetMapping("hello")
    @ApiOperation(value = "你好")
    public Result<Person> hello(){
        return Result.ok(new Person("",1));
    }
}
