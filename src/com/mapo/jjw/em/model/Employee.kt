package com.mapo.jjw.em.model

import com.mapo.jjw.em.HOURLY_RATE
import com.mapo.jjw.em.TAX_RATE
import java.text.NumberFormat
import java.util.*
import kotlin.math.roundToLong
import kotlin.properties.Delegates

open class Employee {
    private var emNo : UUID
    private var emPart : Int
    private var emName by Delegates.notNull<String>()
    private var emDepartment by Delegates.notNull<Department>()
    private var emAge by Delegates.notNull<Int>()
    private var emAddress by Delegates.notNull<String>()
    constructor(emNo : UUID, emPart : Int, emName : String, emDepartment : Department, emAge : Int, emAddress : String) {
        this.emNo = emNo
        this.emPart = emPart
        this.emName = emName
        this.emDepartment = emDepartment
        this.emAge = emAge
        this.emAddress = emAddress
    }
    open fun getEmployeePart() : Int {
        return when(this) {
            is PartTimeEmployee -> 2
            is SalesEmployee -> 1
            is PermanentEmployee, !is SalesEmployee, !is PartTimeEmployee -> 0
            else -> {
                when(this) {
                    is Employee -> 3
                    else -> -1
                }
            }
        }
    }
    open fun getEmployeeNo() : UUID {

        return this.emNo
    }
    open fun getEmployeeDepartment() : Department {
        return this.emDepartment
    }
    open fun modifyEmployeeInformation(emName: String, emDepartment: Department, emAge: Int, emAddress: String) {
        when(emName) {
            else -> this.emName = emName
        }
        println(emName)
        when(emDepartment) {
            else -> this.emDepartment = emDepartment
        }
        println(emDepartment)
        when(emDepartment) {
            else -> this.emAge = emAge
        }
        println(emDepartment)
        when(emDepartment) {
            else -> this.emAddress = emAddress
        }
        println(emAddress)
    }
    open fun updateEmployeeInformation(tempEmployee:Employee) {
        when(tempEmployee.emName) {
            else -> this.emName = tempEmployee.emName
        }
        println(tempEmployee.emName)
        when(tempEmployee.emDepartment) {
            else -> this.emDepartment = tempEmployee.emDepartment
        }
        println(tempEmployee.emDepartment)
        when(tempEmployee.emDepartment) {
            else -> this.emAge = tempEmployee.emAge
        }
        println(tempEmployee.emAge)
        when(tempEmployee.emDepartment) {
            else -> this.emAddress = tempEmployee.emAddress
        }
        println(tempEmployee.emAddress)
    }
    open fun modifyEmployeeSalary() {}
    open fun modifyEmployeeSalary(emSalaryVariable: Long) {}
    open fun calculateSalary() : Long = 0
    override fun toString() : String {
        val emPart : String = when(getEmployeePart()) {
            2 -> "파트 타임"
            1 -> "영업 사원"
            0 -> "정규 사원"
            else -> "오류"
        }
        return "[부서 - ${emDepartment.deptCode} : ${emDepartment.deptName}] | [ 구분 - ${emPart}] | [ 사원 번호 - ${emNo}] | [이름 - ${emName}] | [나이 - ${emAge}] | [주소 - ${emAddress}] | [월급 - ${NumberFormat.getCurrencyInstance(Locale.getDefault()).format(calculateSalary())}]"
    }
}
class PartTimeEmployee : Employee {
    private var emWorkingHour by Delegates.notNull<Long>()
    private var emHourlyRate by Delegates.notNull<Long>()
    constructor(emNo : UUID, emPart: Int, emName : String, emDepartment : Department, emAge : Int, emAddress : String, emWorkingHour : Long) : super(emNo, emPart, emName, emDepartment, emAge, emAddress) {
        this.emWorkingHour = emWorkingHour
        this.emHourlyRate = HOURLY_RATE.toLong()
    }
    override fun modifyEmployeeSalary(emWorkingHour: Long) {
        this.emWorkingHour = emWorkingHour
    }
    override fun calculateSalary() = emWorkingHour.times(emHourlyRate).times(365).times(TAX_RATE).roundToLong()
}
open class PermanentEmployee : Employee {
    var emSalary by Delegates.notNull<Long>()
    constructor(emNo : UUID, emPart: Int, emName : String, emDepartment : Department, emAge : Int, emAddress : String, emSalary : Long) : super(emNo, emPart, emName, emDepartment, emAge, emAddress) {
        this.emSalary = emSalary
    }
    override fun modifyEmployeeSalary(emSalary: Long) {
        this.emSalary = emSalary
    }
    open fun modifyEmployeeSalary(emSalary: Long, emSalesPerformance: Long) {}
    override fun calculateSalary(): Long = (emSalary.div(12) * (1 - TAX_RATE)).roundToLong()
}
class SalesEmployee : PermanentEmployee {
    private var emSalesPerformance by Delegates.notNull<Long>()
    constructor(emNo : UUID, emPart: Int, emName : String, emDepartment : Department, emAge : Int, emAddress : String, emSalary : Long, emSalesPerformance : Long) : super(emNo, emPart, emName, emDepartment, emAge, emAddress, emSalary) {
        this.emSalesPerformance = emSalesPerformance
    }
    override fun modifyEmployeeSalary(emSalary: Long, emSalesPerformance: Long) {
        this.emSalary = emSalary
        this.emSalesPerformance = emSalesPerformance
    }
    override fun calculateSalary() = ((emSalary.div(12) + (emSalesPerformance.times(0.05))) * (1 - TAX_RATE)).roundToLong()
}