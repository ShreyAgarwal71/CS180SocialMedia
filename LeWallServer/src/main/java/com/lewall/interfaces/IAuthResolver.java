package com.lewall.interfaces;

import com.lewall.api.BadRequest;
import com.lewall.api.InternalServerError;
import com.lewall.api.Request;
import com.lewall.dtos.LoginDTO;
import com.lewall.dtos.CreateUserDTO;
import com.lewall.dtos.AuthTokenDTO;

/**
 * Interface implemented only by the AuthResolver class
 *
 * @author Ates Isfendiyaroglu
 * @version 17 November 2024
 */
public interface IAuthResolver {

    /**
     * Sign up with email, password, username, display name, and bio
     * 
     * @throws BadRequest
     *             if email, password, or username is empty
     * @throws BadRequest
     *             if user with given email or username already exists
     * @throws InternalServerError
     *             if unable to register
     * 
     * @param request
     *            {@link Request} with {@link CreateUserDTO} body
     * @return {@link AuthTokenDTO}
     */
    public AuthTokenDTO signUp(Request<CreateUserDTO> request); 

    /**
     * Sign in with email and password
     * 
     * @throws BadRequest
     *             if email or password is empty
     * @throws BadRequest
     *             if no user is found with email and password combination
     * @throws InternalServerError
     *             if unable to login
     * 
     * @param request
     *            {@link Request} with {@link LoginDTO} body
     * @return {@link AuthTokenDTO}
     */
    public AuthTokenDTO signInWithEmailAndPassword(Request<LoginDTO> request);
}
