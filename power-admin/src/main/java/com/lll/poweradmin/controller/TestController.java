package com.lll.poweradmin.controller;

import com.lll.poweradmin.common.Result;
import com.lll.poweradmin.model.domain.Person;
import com.lll.poweradmin.common.Result;
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
